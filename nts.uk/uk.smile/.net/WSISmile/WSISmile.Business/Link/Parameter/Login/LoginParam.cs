using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Login
{
    /// <summary>
    /// ���O�C���p�p�����[�^
    /// </summary>
    [JsonObject]
    public class LoginParam
    {
        #region �����ϐ�
        private string _contractCode = "";

        private string _contractPassword = "";

        private string _companyCode = "";

        private string _employeeCode = "";

        private string _password = "";
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �_��R�[�h
        /// </summary>
        [JsonProperty("contractCode")]
        public string contractCode
        {
            get { return _contractCode; }
            set { _contractCode = value; }
        }

        /// <summary>
        /// �_��p�X���[�h
        /// </summary>
        [JsonProperty("contractPassword")]
        public string contractPassword
        {
            get { return _contractPassword; }
            set { _contractPassword = value; }
        }

        /// <summary>
        /// ��ЃR�[�h
        /// </summary>
        [JsonProperty("companyCode")]
        public string companyCode
        {
            get { return _companyCode; }
            set { _companyCode = value; }
        }

        /// <summary>
        /// �Ј��R�[�h
        /// </summary>
        [JsonProperty("employeeCode")]
        public string employeeCode
        {
            get { return _employeeCode; }
            set { _employeeCode = value; }
        }

        /// <summary>
        /// �Ј��p�X���[�h
        /// </summary>
        [JsonProperty("password")]
        public string password
        {
            get { return _password; }
            set { _password = value; }
        }
        #endregion �v���p�e�B
    }
}
