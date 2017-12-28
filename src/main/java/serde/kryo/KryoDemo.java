package serde.kryo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

public class KryoDemo {

    // Attentions: Must implements java.io.Serializable
    static class User implements Serializable {
        private String name;
        private int id;
        private int age;
        private String email;

        public User(String name, int id, int age, String email) {
            this.name = name;
            this.id = id;
            this.age = age;
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String output_file = "file.bin";

        Kryo kryo = new Kryo();
        // register class
        kryo.register(User.class, new JavaSerializer());

        // serialization
        Output output = new Output(new FileOutputStream(output_file));
        User user = new User("John", 1, 23, "john@company.com");
        kryo.writeObject(output, user);
        output.close();
        // deserialization
        Input input = new Input(new FileInputStream(output_file));
        User read_user = kryo.readObject(input, User.class);
        System.out.println(read_user);
        input.close();
    }
}
