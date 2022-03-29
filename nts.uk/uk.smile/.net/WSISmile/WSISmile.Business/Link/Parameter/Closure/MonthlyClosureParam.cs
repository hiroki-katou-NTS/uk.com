using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// ���ʎ��т̒��ߏ��p�����[�^
    /// </summary>
    [JsonObject]
    public class MonthlyClosureParam
    {
        #region �����ϐ�
        private string _employeeCd = "";

        private int _yearMonth = 0;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �Ј�CD
        /// </summary>
        [JsonProperty("employeeCd")]
        public string employeeCd
        {
            get { return _employeeCd; }
            set { _employeeCd = value; }
        }

        /// <summary>
        /// �����N��
        /// </summary>
        [JsonProperty("yearMonth")]
        public int yearMonth
        {
            get { return _yearMonth; }
            set { _yearMonth = value; }
        }
        #endregion
    }
}
