using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// ���ߊ��ԏ��
    /// </summary>
    public class ClosurePeriodInfo
    {
        #region �R���X�g���N�^
        public ClosurePeriodInfo() { }

        /// <summary>
        /// GetClosurePeriod�̃��X�|���X(JObject)����L���X�g����
        /// </summary>
        /// <param name="aJObject"></param>
        public ClosurePeriodInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["startDate"] != null)
            {
                this._startDate = aJObject.GetValue("startDate").ToObject<DateTime>();
            }
            if (aJObject["endDate"] != null)
            {
                this._endDate = aJObject.GetValue("endDate").ToObject<DateTime>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private int _closureId = 0;

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// ����ID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }

        /// <summary>
        /// ���ߊJ�n��
        /// </summary>
        public DateTime StartDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// ���ߏI����
        /// </summary>
        public DateTime EndDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion �v���p�e�B
    }
}
