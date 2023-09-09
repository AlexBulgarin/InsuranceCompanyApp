package app.insurance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MedicalInsurance extends Insurance {
    public MedicalInsurance(int duration) {
        super(duration);
        this.setPricePerMonth(15);
        this.setTotalPrice(duration * getPricePerMonth());
    }

}

