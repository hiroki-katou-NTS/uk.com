using System;

namespace WSISmile.Business.Link.Parameter.Cookie
{
    /// <summary>
    /// �N�b�L�[���
    /// </summary>
    public class CookieInfo
    {
        #region �R���X�g���N�^
        public CookieInfo() { }

        /// <summary>
        /// �N�b�L�[���쐬
        /// </summary>
        /// <param name="name">�N�b�L�[��</param>
        /// <param name="value">�N�b�L�[�l</param>
        /// <param name="path">�N�b�L�[�p�X</param>
        /// <param name="domain">�N�b�L�[�h���C��</param>
        public CookieInfo(string name, string value, string path, string domain)
        {
            this._name = name;
            this._value = value;
            this._path = path;
            this._domain = domain;
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _name = "";

        private string _value = "";

        private string _path = "";

        private string _domain = "";
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// �N�b�L�[��
        /// </summary>
        public string Name
        {
            get { return _name; }
            set { _name = value; }
        }

        /// <summary>
        /// �N�b�L�[�l
        /// </summary>
        public string Value
        {
            get { return _value; }
            set { _value = value; }
        }

        /// <summary>
        /// �N�b�L�[�p�X
        /// </summary>
        public string Path
        {
            get { return _path; }
            set { _path = value; }
        }

        /// <summary>
        /// �N�b�L�[�h���C��
        /// </summary>
        public string Domain
        {
            get { return _domain; }
            set { _domain = value; }
        }
        #endregion �v���p�e�B
    }
}
