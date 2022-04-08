using System;
using System.Collections.Generic;

using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �w��ٗp(���ߊ���)�ɂ��Ј����o�p�p�����[�^
    /// </summary>
    [JsonObject]
    public class SelectEmployeesByEmpParam
    {
        #region �R���X�g���N�^
        /// <summary>
        /// �w��ٗp(���ߊ���)�ɂ��Ј����o�p�p�����[�^
        /// </summary>
        /// <param name="employmentCd">�ٗpCD</param>
        /// <param name="startDate">���ߊJ�n��</param>
        /// <param name="endDate">���ߏI����</param>
        public SelectEmployeesByEmpParam(string employmentCd, DateTime startDate, DateTime endDate)
        {
            this._employmentCd = new List<string>();
            this._employmentCd.Add(employmentCd);

            this._startDate = startDate;
            this._endDate = endDate;
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private List<string> _employmentCd = new List<string>();

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �ٗpCD List.
        /// </summary>
        [JsonProperty("employmentCd")]
        public List<string> employmentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// ���ߊJ�n��
        /// </summary>
        [JsonProperty("startDate")]
        public DateTime startDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// ���ߏI����
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
