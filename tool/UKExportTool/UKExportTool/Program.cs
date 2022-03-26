using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using UKExportTool.Oruta;
using UKExportTool.Output;

namespace UKExportTool
{
    class Program
    {
        static void Main(string[] args)
        {
            var oruta = new OrutaQuery();
            var setting = new Setting(@"setting.txt");

            WriteCsvLink(setting);
            WriteCsvItems(setting, oruta);
            
        }

        private static void WriteCsvLink(Setting setting)
        {
            var link = new OiomtExOutLinkTable(setting);
            ProcessCsvWriting("OIOMT_EX_OUT_LINK_TABLE.csv", writer =>
            {
                writer.WriteLine("INS_DATE,INS_CCD,INS_SCD,INS_PG,UPD_DATE,UPD_CCD,UPD_SCD,UPD_PG,EXCLUS_VER,CTG_ID,MAIN_TABLE,FORM1,FORM2,CONDITIONS,OUT_COND_ITEM_NAME_1,OUT_COND_ITEM_NAME_2,OUT_COND_ITEM_NAME_3,OUT_COND_ITEM_NAME_4,OUT_COND_ITEM_NAME_5,OUT_COND_ITEM_NAME_6,OUT_COND_ITEM_NAME_7,OUT_COND_ITEM_NAME_8,OUT_COND_ITEM_NAME_9,OUT_COND_ITEM_NAME_10,OUT_COND_ASSOCIATION_1,OUT_COND_ASSOCIATION_2,OUT_COND_ASSOCIATION_3,OUT_COND_ASSOCIATION_4,OUT_COND_ASSOCIATION_5,OUT_COND_ASSOCIATION_6,OUT_COND_ASSOCIATION_7,OUT_COND_ASSOCIATION_8,OUT_COND_ASSOCIATION_9,OUT_COND_ASSOCIATION_10,CONTRACT_CD");
                writer.WriteLine(link.ToCsvRecord());
            });
        }

        private static void WriteCsvItems(Setting setting, OrutaQuery oruta)
        {
            var items = OiomtExOutCtgItemDt.Load(setting, oruta);

            ProcessCsvWriting("OIOMT_EX_OUT_CTG_ITEM_DT.csv", writer =>
            {
                writer.WriteLine("INS_DATE,INS_CCD,INS_SCD,INS_PG,UPD_DATE,UPD_CCD,UPD_SCD,UPD_PG,EXCLUS_VER,TBL_ALIAS,CTG_ID,DATA_TYPE,TABLE_NAME,FIELD_NAME,PRIMARYKEY_CLASSFICATION,DATE_CLASSFICATION,SPECIAL_ITEM,DISPLAY_TABLE_NAME,DISPLAY_CLASSFICATION,CTG_ITEM_NO,ITEM_NAME,REQUIRED_CATEGORY,SEARCH_VALUE_CD,KEYWORD_ATR,CONTRACT_CD");
                foreach (var item in items)
                {
                    writer.WriteLine(item.ToCsvRecord());
                }
            });
        }

        private static void ProcessCsvWriting(string path, Action<StreamWriter> write)
        {
            if (File.Exists(path))
            {
                File.Delete(path);
            }

            using (var writer = new StreamWriter(path, false, Encoding.UTF8))
            {
                write(writer);
            }
        }
    }
}
