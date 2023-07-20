package com.saif.servicecompany.restcontrollers;

import com.saif.servicecompany.dao.CompanyRepository;
import com.saif.servicecompany.entities.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RefreshScope
@RestController
@CrossOrigin("*")
public class CompanyRestController {
    // pour mettre a jour les valeurs des variables, il faut POST: http://localhost:8081/actuator/refresh
    @Value("${xParam}")
    private int xParam;
    @Value("${yParam}")
    private int yParam;
    @Value("${me}")
    private String me;
    private final CompanyRepository companyRepository;

    public CompanyRestController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    @GetMapping("/myConfig")
    public Map<String, Object> myConfig(){
        Map<String, Object> params = new HashMap<>();
        params.put("xParam", xParam);
        params.put("yParam", yParam);
        params.put("me", me);
        params.put("threadName", Thread.currentThread().getName());

        return params;
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanys() {
        List<Company> companys = companyRepository.findAll();
        return new ResponseEntity<>(companys, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        return optionalCompany.map(company -> new ResponseEntity<>(company, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/company/createcompany")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyRepository.save(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isPresent()) {
            companyRepository.delete(optionalCompany.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
