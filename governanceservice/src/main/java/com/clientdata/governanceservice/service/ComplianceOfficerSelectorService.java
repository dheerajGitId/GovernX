package com.clientdata.governanceservice.service;

import com.clientdata.schemas.enums.Nationality;
import com.clientdata.schemas.enums.Users;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ComplianceOfficerSelectorService {

    private static final Random random = new Random();

    public Users getRandomComplianceOfficer(Nationality nationality) {

        List<Users> candidates =
                Arrays.stream(Users.values())
                        .filter(user -> user.getNationality() == nationality)
                        .toList();

        if (candidates.isEmpty()) {
            return Users.OTHERS;
        }

        return candidates.get(random.nextInt(candidates.size()));
    }
}
