package com.safiyaakhtar.application.Activity;

import java.util.Objects;

public class Validation {
    public boolean validatePass(String pass) {
        return pass.trim().length() == 0;
    }

    public boolean passLength(String pass, String pass_con) {
        pass = pass.trim();
        pass_con = pass_con.trim();
        Long a, b;
        a = Long.parseLong(pass);
        b = Long.parseLong(pass_con);
        if (a == b) {
            return true;
        } else return false;
    }

    public boolean answer(String ans) {
        return ans.trim().isEmpty();
    }

}
