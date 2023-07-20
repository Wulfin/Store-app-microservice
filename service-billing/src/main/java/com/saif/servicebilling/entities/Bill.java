package com.saif.servicebilling.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saif.servicebilling.nonentities.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate billingDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long customerID;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private Collection<ProductItem> productItems;
}
@Projection(name = "fullBill",types = Bill.class)
interface BillProjection{
    public Long getId();
    public LocalDate getBillingDate();
    public Long getCustomerID();
    public Collection<ProductItem> getProductItems();
}
