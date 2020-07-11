import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransferStream extends Thread{

    public static volatile Set<Map<String, Department>> setDepartmentsVariants;
    private Map<String, Department> map;

    public TransferStream(Map<String, Department> map) {
        this.map = map;
    }

    private synchronized Map<String, Department> cloneMap(Map<String, Department> srcMap) {
        Map<String, Department> destMap = new HashMap<>();
        for(Map.Entry<String, Department> entry : srcMap.entrySet()) {
            //destMap.put(entry.getKey(), new Department(entry.getValue()));
        }
        return destMap;
    }

    @Override
    public void run() {
        boolean end = true;
        while (end) {
            Map<String, Department> anotherVariantMap = cloneMap(map);
            for (Map.Entry<String, Department> departmentPairOut : anotherVariantMap.entrySet()) {
                for (Map.Entry<String, Department> departmentPairIn : anotherVariantMap.entrySet()) {
                    if (departmentPairOut.getKey().equals(departmentPairIn.getKey()))
                        continue;
                    for (int k = 0; k < departmentPairOut.getValue().listPersonSize(); k++) {
                        if (departmentPairOut.getValue().getPersonFromList(k).getSalary().
                                compareTo(departmentPairOut.getValue().getAverageSalary()) < 0 &&
                                departmentPairOut.getValue().getPersonFromList(k).getSalary().
                                        compareTo(departmentPairIn.getValue().getAverageSalary()) > 0) {
                            departmentPairIn.getValue().addPerson(departmentPairOut.getValue().getPersonFromList(k));
                            departmentPairOut.getValue().removePerson(k);
                            if (setDepartmentsVariants.contains(anotherVariantMap)) {
                                //Если такой отдел есть - меняем местами обратно
                                departmentPairOut.getValue().addPerson(departmentPairIn.getValue().getPersonFromList(departmentPairIn.getValue().listPersonSize() - 1));
                                departmentPairIn.getValue().removePerson(departmentPairIn.getValue().listPersonSize() - 1);
                                continue;
                            }
                            setDepartmentsVariants.add(anotherVariantMap);
                            System.out.println(anotherVariantMap);
                            end = true;

                            Thread thread = new TransferStream(anotherVariantMap);
                            thread.start();

                            Thread.currentThread().interrupt();
                        }
                        end = false;
                    }
                }
            }
        }
        Thread.currentThread().interrupt();
    }
}
