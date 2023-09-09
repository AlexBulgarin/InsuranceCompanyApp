package app;

import app.insurance.InsuranceInfo;
import app.insurance.InsuranceType;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InsuranceCompany {
    private Map<String, Insurant> insurantMap = new HashMap<>();
    private Map<InsuranceType, List<InsuranceInfo>> insuranceMap = new HashMap<>();
}
