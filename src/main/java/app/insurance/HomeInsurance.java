package app.insurance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HomeInsurance extends Insurance {
    public HomeInsurance(int duration) {
        super(duration);
        this.setPricePerMonth(12);
        this.setTotalPrice(duration * getPricePerMonth());
    }
}
