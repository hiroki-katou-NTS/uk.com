using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// ���ʎ��т̒��ߏ��
    /// </summary>
    public class MonthlyClosureInfo
    {
        #region �R���X�g���N�^
        public MonthlyClosureInfo() { }

        /// <summary>
        /// GetMonthlyClosureInfo�̃��X�|���X(JObject)����L���X�g����
        /// </summary>
        /// <param name="aJObject"></param>
        public MonthlyClosureInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["closureDay"] != null)
            {
                this._closureDay = aJObject.GetValue("closureDay").ToObject<int>();
            }
            if (aJObject["lastDay"] != null)
            {
                this._lastDay = aJObject.GetValue("lastDay").ToObject<bool>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private int _closureId = 0;

        private int _closureDay = 0;

        private bool _lastDay = false;
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
        /// ���ߓ�
        /// </summary>
        public int ClosureDay
        {
            get { return _closureDay; }
            set { _closureDay = value; }
        }

        /// <summary>
        /// ������
        /// </summary>
        public bool isLastDay
        {
            get { return _lastDay; }
            set { _lastDay = value; }
        }
        #endregion �v���p�e�B
    }
}
