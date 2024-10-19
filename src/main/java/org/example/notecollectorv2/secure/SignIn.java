package org.example.notecollectorv2.secure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignIn {
    private String email;
    private String password;
}
