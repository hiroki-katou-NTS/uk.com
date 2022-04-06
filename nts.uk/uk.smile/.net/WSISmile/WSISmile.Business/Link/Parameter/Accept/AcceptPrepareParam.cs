using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// �O������O�����p�����[�^
    /// </summary>
    [JsonObject]
    public class AcceptPrepareParam
    {
        #region �����ϐ�
        private string _settingCode = "";

        private string _uploadedCsvFileId = "";
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �������No.
        /// </summary>
        [JsonProperty("settingCode")]
        public string settingCode
        {
            get { return _settingCode; }
            set { _settingCode = value; }
        }

        /// <summary>
        /// ����t�@�C��ID(�A�b�v���[�h�ς�)
        /// </summary>
        [JsonProperty("uploadedCsvFileId")]
        public string uploadedCsvFileId
        {
            get { return _uploadedCsvFileId; }
            set { _uploadedCsvFileId = value; }
        }
        #endregion
    }
}
