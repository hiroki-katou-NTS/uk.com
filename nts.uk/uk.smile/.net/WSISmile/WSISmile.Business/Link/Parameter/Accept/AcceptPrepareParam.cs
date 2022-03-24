using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// 外部受入前準備パラメータ
    /// </summary>
    [JsonObject]
    public class AcceptPrepareParam
    {
        #region 内部変数
        private string _settingCode = "";

        private string _uploadedCsvFileId = "";
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 受入条件No.
        /// </summary>
        [JsonProperty("settingCode")]
        public string settingCode
        {
            get { return _settingCode; }
            set { _settingCode = value; }
        }

        /// <summary>
        /// 受入ファイルID(アップロード済み)
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
