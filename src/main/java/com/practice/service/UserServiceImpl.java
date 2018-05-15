package com.practice.service;

import com.practice.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    static {
        users= populateDummyUsers();
           }

    @Override
    public User findById(long id) {
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for(User user : users){
            if(user.getName().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }

    @Override
    public void deleteUserById(long id) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName()) != null;
    }

    private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(counter.incrementAndGet(),"Naresh",30, 70000));
        users.add(new User(counter.incrementAndGet(),"Shyam",40, 50000));
        users.add(new User(counter.incrementAndGet(),"Ganesh",45, 30000));
        users.add(new User(counter.incrementAndGet(),"Suresh",50, 40000));
        users.add(new User(counter.incrementAndGet(),"Krishna",30, 70000));
        users.add(new User(counter.incrementAndGet(),"Deepmala",40, 50000));
        users.add(new User(counter.incrementAndGet(),"Avinash",45, 30000));
        users.add(new User(counter.incrementAndGet(),"Barun",50, 40000));
        return users;
    }
}
