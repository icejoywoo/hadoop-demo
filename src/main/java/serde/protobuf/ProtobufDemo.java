package serde.protobuf;

import com.github.icejoywoo.protobuf.protos.PersonProtos;

public class ProtobufDemo {
    public static void main(String[] args) {
        PersonProtos.Person john = PersonProtos.Person.newBuilder()
                .setId(1234)
                .setName("John Doe")
                .setEmail("jdoe@example.com")
                .build();
        System.out.println(john.getId());
        System.out.println(john.toString());
    }
}
