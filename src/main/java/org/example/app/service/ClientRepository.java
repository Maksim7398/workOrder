package org.example.app.service;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;
import org.example.app.exeption.NumberCarExeption;
import org.example.web.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
public class ClientRepository implements ProjectRepository<Client> {

    Logger logger = Logger.getLogger(ClientRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;





    @Autowired
    public ClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, List<Client>> retrieveAll() {


        List<Client> clientList = jdbcTemplate.query("SELECT * FROM CLIENT",
                (ResultSet rs, int rowNum) -> {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setFio(rs.getString("fio"));
                    client.setNumberPhone(rs.getString("number_phone"));
                    client.setCarModel(rs.getString("model_car"));
                    client.setNumberCar(rs.getString("number_car"));
                    client.setYearCar(rs.getInt("year_car"));
                    client.setVinCode(rs.getString("vin_code"));
                    client.setMileage(rs.getInt("mileage"));
                    return client;
                });


        Map<String, List<Client>> collect = clientList.stream().collect(Collectors.groupingBy((Client c) ->
                (c.getFio().substring(0, 1))));
        collect = new TreeMap<>(collect);
        return collect;
    }

    @Override
    public void store(Client client) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("fio", client.getFio());
        mapSqlParameterSource.addValue("numberPhone",client.getNumberPhone());
        mapSqlParameterSource.addValue("carModel", client.getCarModel());
        mapSqlParameterSource.addValue("yearCar", client.getYearCar());
        mapSqlParameterSource.addValue("numberCar", client.getNumberCar());
        mapSqlParameterSource.addValue("vinCode", client.getVinCode());
        mapSqlParameterSource.addValue("mileage",client.getMileage());
        jdbcTemplate.update(
                "INSERT INTO client(fio,number_phone,model_car,year_car,number_car,vin_code,mileage) VALUES (:fio,:numberPhone , :carModel, :yearCar, :numberCar, :vinCode , :mileage)", mapSqlParameterSource);
        logger.info("save client in client repo");
    }

    @Override
    public boolean removeItemById(Integer clientIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", clientIdToRemove);
        jdbcTemplate.update("DELETE FROM client WHERE id = :id", parameterSource);
        return true;
    }


    @Override
    public boolean removeItemByRegex(String queryRegex) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();


        for (List<Client> entry : retrieveAll().values()) {
            for (Client client : entry) {
                mapSqlParameterSource.addValue("yearCar", client.getYearCar());

                mapSqlParameterSource.addValue("vinCode", client.getVinCode());
                if (client.getFio().equals(queryRegex)) {
                    mapSqlParameterSource.addValue("fio", client.getFio());
                    logger.info(" DELETE FROM FIO");
                    jdbcTemplate.update("DELETE FROM client WHERE fio = :fio", mapSqlParameterSource);
                    return true;

                } else if (client.getCarModel().equals(queryRegex)) {
                    mapSqlParameterSource.addValue("carModel", client.getCarModel());
                    logger.info(" DELETE FROM MODEL CAR");
                    jdbcTemplate.update("DELETE FROM client WHERE model_car = :carModel", mapSqlParameterSource);
                    return true;
                } else if (client.getNumberCar().equals(queryRegex)) {
                    mapSqlParameterSource.addValue("numberCar", client.getNumberCar());
                    logger.info(" DELETE FROM NUMBER_CAR");
                    jdbcTemplate.update("DELETE FROM client WHERE number_car = :numberCar", mapSqlParameterSource);
                    return true;
                }
            }

        }
        return false;
    }


//        jdbcTemplate.update("DELETE FROM client WHERE id = :queryRegex", mapSqlParameterSource);


    @Override
    public File  replace(Client clientReplace) {
        List<Client> clientList = jdbcTemplate.query("SELECT * FROM CLIENT WHERE id like (select max(id) from client)",
                (ResultSet rs, int rowNum) -> {
                   Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setFio(rs.getString("fio"));
                    client.setNumberPhone(rs.getString("number_phone"));
                    client.setCarModel(rs.getString("model_car"));
                    client.setNumberCar(rs.getString("number_car"));
                    client.setYearCar(rs.getInt("year_car"));
                    client.setVinCode(rs.getString("vin_code"));
                    client.setMileage(rs.getInt("mileage"));
                    return client;
                });




        logger.info(clientReplace);

        String fio = clientReplace.getFio();
        String numberPhone = clientReplace.getNumberPhone();
        String carModel = clientReplace.getCarModel();
        Integer yearCar = clientReplace.getYearCar();
        String numberCar = clientReplace.getNumberCar();
        String vinCode = clientReplace.getVinCode();
        Integer mileage = clientReplace.getMileage();

        logger.info(clientReplace);

        String docFile = "C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\zakaz-naryad.docx";
        File file = new File(docFile);
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = doc.getParagraphs();


            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun run : p.getRuns()) {
                                String text = run.getText(0);
                                text = text.replace("FIO", fio);
                                text = text.replace("numberPhone", numberPhone);
                                text = text.replace("newDate", new Date().toString());
                                text = text.replace("markaModel", carModel);
                                text = text.replace("year", yearCar.toString());
                                text = text.replace("госномер", numberCar);
                                text = text.replace("vinCode", vinCode);
                                text = text.replace("mileage", mileage.toString());



                                run.setText(text, 0);
                            }
                        }
                    }
                }

            }


            for (XWPFParagraph p : paragraphs) {
                List<XWPFRun> runs = p.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = text.replace("FIO", fio);
                    }
                    run.setText(text, 0);

                }

            }



            doc.write(Files.newOutputStream
                    (Paths.get( "C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\outZakaz.docx")));

            return new File( "C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\outZakaz.docx");



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    public Client autoGeneration(String numberCar) throws NumberCarExeption {
        List<Client> clientList = jdbcTemplate.query(("SELECT * FROM CLIENT WHERE number_car LIKE " + "'" + numberCar + "'"),
                (ResultSet rs, int rowNum) -> {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setFio(rs.getString("fio"));
                    client.setNumberPhone(rs.getString("number_phone"));
                    client.setCarModel(rs.getString("model_car"));
                    client.setNumberCar(rs.getString("number_car"));
                    client.setYearCar(rs.getInt("year_car"));
                    client.setVinCode(rs.getString("vin_code"));
                    client.setMileage(rs.getInt("mileage"));

                    return client;
                });
        try {
            return clientList.get(0);
        } catch (Exception exception) {
           throw new NumberCarExeption("номера нет в базе данных");
        }
    }

    @Override
    public List<Client> searchClientToName(String name) {
        List<Client> clientList = jdbcTemplate.query(("SELECT * FROM CLIENT WHERE fio LIKE " + "'" + name + "'"),
                (ResultSet rs, int rowNum) -> {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setFio(rs.getString("fio"));
                    client.setNumberPhone(rs.getString("number_phone"));
                    client.setCarModel(rs.getString("model_car"));
                    client.setNumberCar(rs.getString("number_car"));
                    client.setYearCar(rs.getInt("year_car"));
                    client.setVinCode(rs.getString("vin_code"));
                    client.setMileage(rs.getInt("mileage"));

                    return client;
                });
        return clientList;
                }
    }




