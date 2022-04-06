using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// ���ʎ��т̏��F��Ԃ̃`�F�b�N�p�p�����[�^
    /// </summary>
    [JsonObject]
    public class ApproveStatusCheckParam
    {
        #region �����ϐ�
        private string _employeeCd = "";

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;

        private int _yearMonth = 0;

        private int _closureID = 0;

        private int _closureDay = 0;

        private bool _lastDayOfMonth = false;

        private DateTime _baseDate = DateTime.Today;
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

        /// <summary>
        /// �N��
        /// </summary>
        [JsonProperty("yearMonth")]
        public int yearMonth
        {
            get { return _yearMonth; }
            set { _yearMonth = value; }
        }

        /// <summary>
        /// ����ID
        /// </summary>
        [JsonProperty("closureID")]
        public int closureID
        {
            get { return _closureID; }
            set { _closureID = value; }
        }

        /// <summary>
        /// ���ߓ�
        /// </summary>
        [JsonProperty("closureDay")]
        public int closureDay
        {
            get { return _closureDay; }
            set { _closureDay = value; }
        }

        /// <summary>
        /// ������
        /// </summary>
        [JsonProperty("lastDayOfMonth")]
        public bool lastDayOfMonth
        {
            get { return _lastDayOfMonth; }
            set { _lastDayOfMonth = value; }
        }

        /// <summary>
        /// ���
        /// </summary>
        [JsonProperty("baseDate")]
        public DateTime baseDate
        {
            get { return _baseDate; }
            set { _baseDate = value; }
        }
        #endregion �v���p�e�B
    }
}
