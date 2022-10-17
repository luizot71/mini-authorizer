package br.com.api.vr.benefits.miniauthorizer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BenefitsCardStatus {

     ACTIVE_BENEFITS_CARD("ACTIVE"),
     INACTIVE_BENEFITS_CARD("INACTIVE");

    private String value;

    public static BenefitsCardStatus getValues(String value) {
        return Arrays.stream( values() )
                .filter( v -> v.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse( null );
    }
}
