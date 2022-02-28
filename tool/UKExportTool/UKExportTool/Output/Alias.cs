using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UKExportTool.Output
{
    class Alias
    {
        public static string Main { get { return "M"; } }

        public static string Join(int index) { return $"J{index}"; }
    }
}
