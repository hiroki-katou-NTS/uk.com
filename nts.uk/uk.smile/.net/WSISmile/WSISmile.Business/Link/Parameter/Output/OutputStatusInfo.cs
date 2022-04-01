using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// �O���o�͏�����ԁ����s����(��������)
    /// </summary>
    public class OutputStatusInfo
    {
        #region �R���X�g���N�^
        public OutputStatusInfo() { }

        /// <summary>
        /// OutputStatusCheck�̃��X�|���X(JObject)����L���X�g����
        /// </summary>
        /// <param name="aJObject"></param>
        public OutputStatusInfo(JObject aJObject)
        {
            if (aJObject["exOutProId"] != null)
            {
                this._exOutProId = aJObject.GetValue("exOutProId").ToObject<string>();
            }
            if (aJObject["proCnt"] != null)
            {
                this._proCnt = aJObject.GetValue("proCnt").ToObject<int>();
            }
            if (aJObject["errCnt"] != null)
            {
                this._errCnt = aJObject.GetValue("errCnt").ToObject<int>();
            }
            if (aJObject["totalProCnt"] != null)
            {
                this._totalProCnt = aJObject.GetValue("totalProCnt").ToObject<int>();
            }
            if (aJObject["doNotInterrupt"] != null)
            {
                this._doNotInterrupt = Convert.ToBoolean(aJObject.GetValue("doNotInterrupt").ToObject<int>());
            }
            if (aJObject["proUnit"] != null)
            {
                this._proUnit = aJObject.GetValue("proUnit").ToObject<string>();
            }
            if (aJObject["opCond"] != null)
            {
                this._opCond = (OutputStatus)System.Enum.ToObject(typeof(OutputStatus), aJObject.GetValue("opCond").ToObject<int>());
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _exOutProId = "";

        private int _proCnt = 0;

        private int _errCnt = 0;

        private int _totalProCnt = 0;

        private bool _doNotInterrupt = false;

        private string _proUnit = "";

        private OutputStatus _opCond = OutputStatus.None;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// TaskID
        /// </summary>
        public string ExOutProId
        {
            get { return _exOutProId; }
            set { _exOutProId = value; }
        }

        /// <summary>
        /// �����J�E���g
        /// </summary>
        public int ProCnt
        {
            get { return _proCnt; }
            set { _proCnt = value; }
        }

        /// <summary>
        /// �G���[����
        /// </summary>
        public int ErrCnt
        {
            get { return _errCnt; }
            set { _errCnt = value; }
        }

        /// <summary>
        /// ��������
        /// </summary>
        public int TotalProCnt
        {
            get { return _totalProCnt; }
            set { _totalProCnt = value; }
        }

        /// <summary>
        /// �������f�t���O
        /// </summary>
        public bool DoNotInterrupt
        {
            get { return _doNotInterrupt; }
            set { _doNotInterrupt = value; }
        }

        /// <summary>
        /// �����P��
        /// </summary>
        public string ProUnit
        {
            get { return _proUnit; }
            set { _proUnit = value; }
        }

        /// <summary>
        /// �O���o�͏������
        /// </summary>
        public OutputStatus OpCond
        {
            get { return _opCond; }
            set { _opCond = value; }
        }
        #endregion �v���p�e�B
    }
}
