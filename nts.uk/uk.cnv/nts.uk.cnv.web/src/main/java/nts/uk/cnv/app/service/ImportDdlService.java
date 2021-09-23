//package nts.uk.cnv.app.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.error.BusinessException;
//import nts.uk.cnv.app.command.ErpTableDesignImportCommand;
//import nts.uk.cnv.app.command.ErpTableDesignImportCommand;
//import nts.uk.cnv.app.command.ErpTableDesignImportCommandHandler;
//import nts.uk.cnv.app.dto.ImportFromFileDto;
//import nts.uk.cnv.app.dto.ImportFromFileResult;
//import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;
//
//@Stateless
//public class ImportDdlService {
//	@Inject
//	public ErpTableDesignRepository erpTableDesignRepository;
//	@Inject
//	private ErpTableDesignImportCommandHandler handler;
//	@Inject
//	TransactionService transaction;
//
//    public ImportFromFileResult importErpFromFile(ImportFromFileDto params) {
//
//        ImportFromFileResult result = new ImportFromFileResult(0, new ArrayList<>());
//
//        //フォルダからファイルリストを取得する
//        File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));
//
//        if( folder.isFile() ) {
//            try {
//                List<Map<String, String>> createTableSql = readFile(folder.toString(), params.getType(), true);
//
//                for (Map<String, String> ddl : createTableSql) {
//                    ErpTableDesignImportCommand command = new ErpTableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());
//                    handler.handle(command);
//                    result.increment();
//                }
//                System.out.println("ファイル名[" + folder.toString() + "]を取り込みました。");
//            }
//            catch (Exception ex) {
//                result.getErroMessages().add("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
//                System.out.println("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
//            }
//
//            return result;
//        }
//
//        File files[] = folder.listFiles();
//        if (files == null || files.length == 0) {
//            throw new BusinessException("ファイルが存在しません。");
//        }
//
//        for (File file : folder.listFiles()) {
//        	if(!file.isFile()) continue;
//
//        	if(!(file.getName()).toUpperCase().endsWith(".SQL")) continue;
//
//            String inProcessingSql = "";
//            List<Map<String, String>> createTableSql = new ArrayList<>();
//            try {
//                createTableSql = readFile(file.toString(), params.getType(), true);
//            }
//            catch (Exception ex) {
//                result.getErroMessages().add("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
//                System.out.println("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
//
//                Path dest = Paths.get(folder.toString() + "\\errorlist\\" + file.getName());
//                try {
//                    Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException e) {
//
//                }
//            }
//
//            for (Map<String, String> ddl : createTableSql) {
//
//                try {
//                    inProcessingSql = ddl.get("CREATE TABLE");
//                    ErpTableDesignImportCommand command = new ErpTableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());
//                    handler.handle(command);
//
//                    result.increment();
//                }
//                catch(Exception ex) {
//                    result.getErroMessages().add("以下のSQLの取り込みに失敗しました。:\r\n" + inProcessingSql + " " + ex.getMessage());
//                    System.out.println("以下のSQLの取り込みに失敗しました。:\r\n" + inProcessingSql + " " + ex.getMessage());
//                }
//            }
//            System.out.println("ファイル名[" + file.toString() + "]を取り込みました。");
//        }
//        return result;
//    }
//
//    private List<Map<String, String>> readFile(String filename, String type, boolean onlyCreateTable) throws IOException {
//
//        Path path = Paths.get(filename);
//        List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
//
//        List<String> createTablesqls =
//            Arrays.stream(
//                String.join("\r\n", text)
//                .replace("[int]", "[int(10)]")
//                .replace("[", "")
//                .replace("]", "")
//                //.replaceAll("CHECK \\(( |[A-Z]|[a-z]|[0-9]|=|>|<|_)*?\\)", "")
//                .split("CREATE TABLE")
//            )
//            .filter(s -> !s.isEmpty())
//            .map(s -> "CREATE TABLE" + s)
//            .collect(Collectors.toList());
//
//        List<Map<String, String>> resultList = new ArrayList<>();
//        for (String sentence : createTablesqls) {
//            Map<String, String> result = new HashMap<>();
//            result.put("CREATE TABLE", "");
//            result.put("CREATE INDEX", "");
//            result.put("COMMENT", "");
//
//            String[] block;
//            if(type.equals("sqlserver")) {
//                block = sentence
//                        .replaceAll("GO\r\n", ";")
//                        .replaceAll("GO$", ";")
//                        .replaceAll("WITH \\(( |[A-Z]|[a-z]|[0-9]|=|,|_)*?\\) ON ([A-Z]|[a-z])*?\r\n", "\r\n")
//                        .replaceAll("WITH \\(( |[A-Z]|[a-z]|[0-9]|=|,|_)*?\\) ON ([A-Z]|[a-z]|,)*?\r\n", ",\r\n")
//                        .replaceAll("\\) ON [A-Z]*", ")")
//                        .split(";");
//            }
//            else {
//                block = String.join("\r\n", sentence).split(";");
//            }
//
//            if(block.length > 0) {
//                for (int i=0; i<block.length; i++) {
//                    if(block[i].contains("CREATE TABLE")) {
//                        result.put("CREATE TABLE", block[i] + ";");
//                    }
//                    else if(!onlyCreateTable && block[i].contains("CREATE") && block[i].contains("INDEX")) {
//                        if (result.get("CREATE INDEX").isEmpty()) {
//                            result.put("CREATE INDEX", block[i] + ";");
//                        }
//                        else {
//                            result.put("CREATE INDEX", result.get("CREATE INDEX") + block[i] + ";");
//                        }
//                    }
//                    else if(!onlyCreateTable && block[i].contains("COMMENT")) {
//                        if (result.get("COMMENT").isEmpty()) {
//                            result.put("COMMENT", block[i] + ";");
//                        }
//                        else {
//                            result.put("COMMENT", result.get("COMMENT") + block[i] + ";");
//                        }
//                    }
//                }
//            }
//
//            resultList.add(result);
//        }
//
//        return resultList;
//    }
//}