using System;
using Newtonsoft.Json.Linq;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// ���F�t�F�[�Y���
    /// </summary>
    public class ApprovalPhaseStateInfo
    {
        #region �R���X�g���N�^
        public ApprovalPhaseStateInfo(JObject aJObject)
        {
            if (aJObject["approvalAtr"] != null)
            {
                this._approvalAtr = Convert.ToBoolean(aJObject.GetValue("approvalAtr").ToObject<int>());
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private bool _approvalAtr = false;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// ���F�敪
        /// </summary>
        public bool ApprovalAtr
        {
            get { return _approvalAtr; }
            set { _approvalAtr = value; }
        }
        #endregion �v���p�e�B
    }
}
