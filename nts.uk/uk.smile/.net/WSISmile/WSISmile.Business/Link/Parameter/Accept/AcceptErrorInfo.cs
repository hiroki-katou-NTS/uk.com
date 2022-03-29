using System;
using Newtonsoft.Json.Linq;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// �O������G���[���
    /// </summary>
    public class AcceptErrorInfo
    {
        #region �R���X�g���N�^
        public AcceptErrorInfo() { }

        public AcceptErrorInfo(JObject aJObject)
        {
            if (aJObject["text"] != null)
            {
                this._text = aJObject.GetValue("text").ToObject<string>();
            }
            if (aJObject["errorsCount"] != null)
            {
                this._errorsCount = aJObject.GetValue("errorsCount").ToObject<int>();
            }
        }
        #endregion

        #region �����ϐ�
        private string _text = "";

        private int _errorsCount = 0;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �G���[���e
        /// </summary>
        public string Text
        {
            get { return _text; }
            set { _text = value; }
        }

        /// <summary>
        /// �G���[��
        /// </summary>
        public int ErrorsCount
        {
            get { return _errorsCount; }
            set { _errorsCount = value; }
        }
        #endregion �v���p�e�B
    }
}
