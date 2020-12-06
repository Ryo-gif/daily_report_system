package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();
        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        if (!code_error.equals("")) {
            errors.add(code_error);
        }
        String name_error = _validateName(e.getName());
        if (!name_error.equals("")) {
            errors.add(name_error);
        }
        String password_error = _validatePassword(e.getPassword(), password_check_flag);
        if (!password_error.equals("")) {
            errors.add(password_error);
        }
        return errors;
    }
 // 社員番号
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
    // 必須入力
    if (code == null || code.equals("")) {
    return "社員番号を入力して下さい。";
    }
    // 登録社員番号重複
    if (code_duplicate_check_flag) {
    EntityManager em = DBUtil.createEntityManager();
    long employees_count = (long) em.createNamedQuery("checkRegisteredCode", Long.class)
    .setParameter("code", code).getSingleResult();
    em.close();
    if (employees_count > 0) {
    return "入力された社員番号の情報はすでに存在しています。";
    }
    }
    return "";
    }

    // 社員名必須入力
    private static String _validateName(String name) {
    if (name == null || name.equals("")) {
    return "氏名を入力して下さい。";
    }
    return "";
    }

    // 必須入力
    private static String _validatePassword(String password, Boolean password_check_flag) {
    // 変更場合実行
    if (password_check_flag && (password == null || password.equals(""))) {
    return "パスワードを入力して下さい。";
    }
    return "";
    }
}
