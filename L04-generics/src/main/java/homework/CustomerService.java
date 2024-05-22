package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerServices;
    public CustomerService() {
        customerServices = new TreeMap<>(Comparator.comparingLong(o -> o.getScores()));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Customer smallestCustomer = customerServices.navigableKeySet().stream().findFirst().get();
        Customer newCustomer = new Customer(smallestCustomer);
        String smallestData =  customerServices.entrySet().stream().filter(v -> v.getKey().equals(smallestCustomer))
                .findFirst()
                .map(v-> v.getValue()).get();
        return new AbstractMap.SimpleEntry<Customer, String>(
                newCustomer,
                smallestData);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        long customerScores = customer.getScores();
        Map.Entry<Customer, String> customerService = customerServices.entrySet()
                .stream()
                .filter(f -> f.getKey().getScores() > customerScores)
                .findFirst()
                .orElse(null);
        return customerService == null
            ? null
            : new AbstractMap.SimpleEntry<Customer, String>(
                new Customer(customerService.getKey()),
                customerServices.get(customerService.getKey()));
    }

    public void add(Customer customer, String data) {
        customerServices.put(customer, data);
    }
}
