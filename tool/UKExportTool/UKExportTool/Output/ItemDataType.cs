using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UKExportTool.Oruta;

namespace UKExportTool.Output
{
    class ItemDataType
    {
        public static readonly ItemDataType 数値 = new ItemDataType(0);
        public static readonly ItemDataType 文字 = new ItemDataType(1);
        public static readonly ItemDataType 日付 = new ItemDataType(2);
        public static readonly ItemDataType 時間 = new ItemDataType(3);
        public static readonly ItemDataType 時刻 = new ItemDataType(4);
        public static readonly ItemDataType 在職区分 = new ItemDataType(7);

        public readonly int value;

        private ItemDataType(int value)
        {
            this.value = value;
        }

        public static ItemDataType Infer(OrutaColumn column)
        {
            string orutaDataType = column.dataType.ToLower();
            if (orutaDataType.Contains("char")) return 文字;
            if (orutaDataType == "int" || orutaDataType == "real") return InferNumeric(column.nameJp);
            if (orutaDataType == "date") return 日付;

            throw new Exception($"not supported: {orutaDataType}, {column.nameJp}");
        }

        private static ItemDataType InferNumeric(string itemName)
        {
            if (itemName.Contains("時間")) return 時間;
            if (itemName.Contains("時刻")) return 時間;
            return 数値;
        }
    }
}
