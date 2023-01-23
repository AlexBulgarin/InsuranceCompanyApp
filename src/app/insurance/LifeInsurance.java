package app.insurance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LifeInsurance extends Insurance {
    public LifeInsurance(int duration) {
        super(duration);
        this.setPricePerMonth(20);
        this.setTotalPrice(duration * getPricePerMonth());
    }
}
