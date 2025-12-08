package com.boundiou.utils;

import com.boundiou.dao.UserDAO;
import com.boundiou.models.User;

public class TestUserDAO {
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();

            // 1) Ajouter un utilisateur
            User u = new User("choupi", "1234", "ADMIN");
            dao.addUser(u);
            System.out.println("✅ User ajouté !");

            // 2) Le retrouver par son username
            User found = dao.findByUsername("choupi");
            System.out.println("🔎 User trouvé : " + found);

            // 3) Afficher tous les users
            System.out.println("📋 Tous les users :");
            for (User user : dao.getAllUsers()) {
                System.out.println(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
