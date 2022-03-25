using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// �ٗp�^���ߏ��
    /// </summary>
    public class EmploymentClosureInfo
    {
        #region �R���X�g���N�^
        public EmploymentClosureInfo() { }

        /// <summary>
        /// GetEmploymentClosureInfo�̃��X�|���X(JObject)����L���X�g����
        /// </summary>
        /// <param name="aJObject"></param>
        public EmploymentClosureInfo(JObject aJObject)
        {
            if (aJObject["employmentCd"] != null)
            {
                this._employmentCd = aJObject.GetValue("employmentCd").ToObject<string>();
            }
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _employmentCd = "";

        private int _closureId = 0;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �ٗp�R�[�h
        /// </summary>
        public string EmploymentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// ����ID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }
        #endregion �v���p�e�B
    }
}
