package app.insurance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AutoInsurance extends Insurance {
    public AutoInsurance(int duration) {
        super(duration);
        this.setPricePerMonth(8);
        this.setTotalPrice(duration * getPricePerMonth());
    }
}
