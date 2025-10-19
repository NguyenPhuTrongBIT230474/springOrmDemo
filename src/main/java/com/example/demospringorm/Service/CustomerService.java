package com.example.demospringorm.Service;

import com.example.demospringorm.Entity.Customer;
import com.example.demospringorm.Reporitory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void testAllQueries() {

        System.out.println("\nTao du lieu mau");

        Customer c1 = new Customer(); c1.setName("An Nguyen"); c1.setAge(25); c1.setEmail("an@gmail.com"); c1.setSDT("0123456789");
        Customer c2 = new Customer(); c2.setName("Binh Tran"); c2.setAge(30); c2.setEmail("binh@yahoo.com"); c2.setSDT("0919876543");
        Customer c3 = new Customer(); c3.setName("An Nguyen"); c3.setAge(25); c3.setEmail("an2@gmail.com"); c3.setSDT("0900000000");
        Customer c4 = new Customer(); c4.setName("Cuong Le"); c4.setAge(18); c4.setEmail("cuong@mail.com"); c4.setSDT("0155555555");
        Customer c5 = new Customer(); c5.setName("Dat Pham"); c5.setAge(40); c5.setEmail("dat@google.com"); c5.setSDT("0987654321");
        Customer c6 = new Customer(); c6.setName("Dat Pham"); c6.setAge(30); c6.setEmail("dat2@google.com"); c6.setSDT("0999999999");


        customerRepository.deleteAll();
        customerRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));

        Long c1Id = c1.getId();
        Long c2Id = c2.getId();
        Long c4Id = c4.getId();

        System.out.println("tong so khach cua khi them:  " + customerRepository.count());

        System.out.println(" Ten 'An Nguyen': " + customerRepository.findByName("An Nguyen"));

        System.out.println(" tuoi > 30: " + customerRepository.findByAgeGreaterThan(30));

        System.out.println(" ten chua 'dat' & email chua 'google': " +
                customerRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase("dat", "google"));

        System.out.println("top 5 khach tre nhat " + customerRepository.findTop5ByOrderByAgeAsc());

        System.out.println("SDT '0919876543' ton tai: : " + customerRepository.existsBySDT("0919876543"));

        Optional<Customer> foundByEmail = customerRepository.findByEmailJPQL("cuong@mail.com");
        System.out.println("tim theo emmail 'cuong@mail.com': " + foundByEmail.orElse(null));

        System.out.println("tim trong tuoi tu 20 den 30 " + customerRepository.findByAgeRange(20, 30));

        List<Object[]> nameAndAge = customerRepository.findNameAndAge();
        System.out.println(" (List<Object[]>): " + formatObjectArray(nameAndAge));

        System.out.println("sdt bat dau bang '01': " + customerRepository.countCustomersSDTStartWith01());

        System.out.println("sap xep theo tuoi giam dan:  " + customerRepository.findAllCustomerAgeOrderedDESC());


        System.out.println("tim kiem theo keyword Pham:  " + customerRepository.searchByKeywordNameNative("Pham"));

        System.out.println("tim id [" + c1Id + ", " + c4Id + "]: " + customerRepository.findByIdsInList(Arrays.asList(c1Id, c4Id)));

        System.out.println("ngoai tru Id" + c1Id + ": " + customerRepository.findAllExceptId(c1Id));

        int updatedRows = customerRepository.updateEmailById(c2Id, "binh.new@gmail.com");
        System.out.println("cap nhat ID=" + c2Id + ": so hang anh huong: " + updatedRows);
        System.out.println("   Kiem tra email moi:  " + customerRepository.findById(c2Id).get().getEmail());

        int deletedRows = customerRepository.deleteCustomersByAgeLessThan(20);
        System.out.println("xoa khach hang duoi 20 tuoi. So khach con lai: " + deletedRows);
        System.out.println("  tong so con lai: " + customerRepository.count());

        System.out.println("tuoi nho nhat (MIN): " + customerRepository.findMinAge());

        System.out.println("tuoi trung binh (AVG): " + customerRepository.findAverageAge());

        List<Object[]> ageCounts = customerRepository.countCustomersByAge();
        System.out.println("dem theo tuoi (GROUP BY): " + formatObjectArray(ageCounts));

        System.out.println("tuoi co >1 người (HAVING): " + formatObjectArray(customerRepository.countCustomersByAgeHavingDuplicates()));

        System.out.println("Email 'dat@google.com' ton tai (EXISTS)?: " + customerRepository.existsByEmailQuery("dat@google.com"));

        System.out.println("Email cua 'An Nguyen': " + customerRepository.findEmailsByName("An Nguyen"));
    }

    private String formatObjectArray(List<Object[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object[] row : list) {
            sb.append(Arrays.toString(row)).append(", ");
        }
        if (list.size() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}