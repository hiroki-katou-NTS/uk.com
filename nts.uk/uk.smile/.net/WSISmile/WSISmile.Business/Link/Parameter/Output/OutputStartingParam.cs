using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �O���o�͋N���p�p�����[�^
    /// </summary>
    [JsonObject]
    public class OutputStartingParam
    {
        #region �����ϐ�
        private string _conditionSetCd = "";

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �o�͏���No.
        /// </summary>
        [JsonProperty("conditionSetCd")]
        public string conditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
        }

        /// <summary>
        /// �J�n��
        /// </summary>
        [JsonProperty("startDate")]
        public DateTime startDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// �I����
        /// </summary>
        [JsonProperty("endDate")]
        public DateTime endDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion �v���p�e�B
    }
}
