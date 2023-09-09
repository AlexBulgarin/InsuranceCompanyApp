package app.insurance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = AutoInsurance.class, name = "AUTO"),
        @JsonSubTypes.Type(value = HomeInsurance.class, name = "HOME"),
        @JsonSubTypes.Type(value = LifeInsurance.class, name = "LIFE"),
        @JsonSubTypes.Type(value = MedicalInsurance.class, name = "MEDICAL"),
        @JsonSubTypes.Type(value = TravelInsurance.class, name = "TRAVEL")})
public abstract class Insurance {
    private int duration;
    private int pricePerMonth;
    private int totalPrice;

    public Insurance(int duration) {
        this.duration = duration;
    }
}
