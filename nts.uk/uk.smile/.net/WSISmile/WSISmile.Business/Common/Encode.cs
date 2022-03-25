using System;
using System.Text;

namespace WSISmile.Business.Common
{
    /// <summary>
    /// Encoding
    /// </summary>
    public class Encode
    {
        /// <summary>
        /// Encoding.Default
        /// </summary>
        public static Encoding Text = Encoding.Default;

        /// <summary>
        /// Shift_JIS
        /// </summary>
        public static Encoding Shift_JIS = Encoding.GetEncoding("Shift_JIS");
    }
}
