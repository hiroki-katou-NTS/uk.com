using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// ���ߏ��
    /// </summary>
    public class ClosureInfo
    {
        #region �R���X�g���N�^
        public ClosureInfo() { }

        /// <summary>
        /// GetClosureInfo�̃��X�|���X(JObject)����L���X�g����
        /// </summary>
        /// <param name="aJObject"></param>
        public ClosureInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["useAtr"] != null)
            {
                this._useAtr = Convert.ToBoolean(aJObject.GetValue("useAtr").ToObject<int>());
            }
            if (aJObject["closuresMonth"] != null)
            {
                this._closuresMonth = aJObject.GetValue("closuresMonth").ToObject<int>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private int _closureId = 0;

        private bool _useAtr = false;

        private int _closuresMonth = 0;
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
        /// ���p�敪
        /// </summary>
        public bool UseAtr
        {
            get { return _useAtr; }
            set { _useAtr = value; }
        }

        /// <summary>
        /// ����
        /// </summary>
        public int CurrentMonth
        {
            get { return _closuresMonth; }
            set { _closuresMonth = value; }
        }
        #endregion �v���p�e�B
    }
}
