package serde.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import example.avro.User;

public class AvroDemo {
    public static void main(String[] args) throws IOException {
        // Serializing and deserializing with code generation
        {
            User user1 = new User();
            user1.setName("Alyssa");
            user1.setFavoriteNumber(256);
            // Leave favorite color null

            // Alternate constructor
            User user2 = new User("Ben", 7, "red");

            // Construct via builder
            User user3 = User.newBuilder()
                    .setName("Charlie")
                    .setFavoriteColor("blue")
                    .setFavoriteNumber(null)
                    .build();

            File file = new File("users.avro");
            if (file.exists()) {
                file.delete();
            }

            // Serialize user1, user2 and user3 to disk
            DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
            DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
            dataFileWriter.create(User.SCHEMA$, file); // user1.getSchema()
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
            dataFileWriter.close();

            // Deserialize Users from disk
            DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
            DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
            User user = null;
            while (dataFileReader.hasNext()) {
                // Reuse user object by passing it to next(). This saves us from
                // allocating and garbage collecting many objects for files with
                // many items.
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        }

        System.out.println("==============================================");

        // Serializing and deserializing without code generation
        {
            Schema schema = new Schema.Parser().parse(new File("src/main/avro/user.avsc"));
            System.out.println(schema.toString());

            GenericRecord user1 = new GenericData.Record(schema);
            user1.put("name", "Alyssa");
            user1.put("favorite_number", 256);
            // Leave favorite color null

            GenericRecord user2 = new GenericData.Record(schema);
            user2.put("name", "Ben");
            user2.put("favorite_number", 7);
            user2.put("favorite_color", "red");

            File file = new File("users.avro");
            if (file.exists()) {
                file.delete();
            }

            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
            dataFileWriter.create(schema, file);
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.close();

            // Deserialize users from disk
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
            GenericRecord user = null;
            while (dataFileReader.hasNext()) {
                // Reuse user object by passing it to next(). This saves us from
                // allocating and garbage collecting many objects for files with
                // many items.
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        }

    }
}
