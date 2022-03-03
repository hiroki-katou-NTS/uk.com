using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Output
{
    class Csv
    {
        public static string ToCsvLine(IEnumerable<object> values)
        {
            var metaValues = new object[]
            {
                null, null, null, null,
                null, null, null, null,
                0,
            };

            var csvValues = metaValues.Concat(values)
                .Select(ToCsvValue)
                .ToList();
            
            return string.Join(",", csvValues);
        }

        public static string ToCsvValue(object value)
        {
            if (value == null)
            {
                return "";
            }

            if (value is string)
            {
                return $"\"{value}\"";
            }

            return value.ToString();
        }
    }
}
