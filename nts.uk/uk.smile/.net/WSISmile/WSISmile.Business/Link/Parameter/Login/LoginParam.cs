using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Login
{
    /// <summary>
    /// ログイン用パラメータ
    /// </summary>
    [JsonObject]
    public class LoginParam
    {
        #region 内部変数
        private string _contractCode = "";

        private string _contractPassword = "";

        private string _companyCode = "";

        private string _employeeCode = "";

        private string _password = "";
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 契約コード
        /// </summary>
        [JsonProperty("contractCode")]
        public string contractCode
        {
            get { return _contractCode; }
            set { _contractCode = value; }
        }

        /// <summary>
        /// 契約パスワード
        /// </summary>
        [JsonProperty("contractPassword")]
        public string contractPassword
        {
            get { return _contractPassword; }
            set { _contractPassword = value; }
        }

        /// <summary>
        /// 会社コード
        /// </summary>
        [JsonProperty("companyCode")]
        public string companyCode
        {
            get { return _companyCode; }
            set { _companyCode = value; }
        }

        /// <summary>
        /// 社員コード
        /// </summary>
        [JsonProperty("employeeCode")]
        public string employeeCode
        {
            get { return _employeeCode; }
            set { _employeeCode = value; }
        }

        /// <summary>
        /// 社員パスワード
        /// </summary>
        [JsonProperty("password")]
        public string password
        {
            get { return _password; }
            set { _password = value; }
        }
        #endregion プロパティ
    }
}
