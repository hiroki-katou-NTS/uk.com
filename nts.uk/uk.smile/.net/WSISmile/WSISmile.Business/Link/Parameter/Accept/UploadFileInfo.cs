using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// �t�@�C�����(�A�b�v���[�h�ς�)
    /// </summary>
    public class UploadFileInfo
    {
        #region �R���X�g���N�^
        public UploadFileInfo() { }

        public UploadFileInfo(JObject aJObject)
        {
            if (aJObject["id"] != null)
            {
                this._id = aJObject.GetValue("id").ToObject<string>();
            }
            if (aJObject["originalName"] != null)
            {
                this._originalName = aJObject.GetValue("originalName").ToObject<string>();
            }
            if (aJObject["fileType"] != null)
            {
                this._fileType = aJObject.GetValue("fileType").ToObject<string>();
            }
            if (aJObject["mimeType"] != null)
            {
                this._mimeType = aJObject.GetValue("mimeType").ToObject<string>();
            }
            if (aJObject["originalSize"] != null)
            {
                this._originalSize = aJObject.GetValue("originalSize").ToObject<int>();
            }
            if (aJObject["zipEntryFile"] != null)
            {
                this._zipEntryFile = aJObject.GetValue("zipEntryFile").ToObject<bool>();
            }
            if (aJObject["temporary"] != null)
            {
                this._temporary = aJObject.GetValue("temporary").ToObject<bool>();
            }
        }
        #endregion �R���X�g���N�^

        #region �����ϐ�
        private string _id = "";

        private string _originalName = "";

        private string _fileType = "";

        private string _mimeType = "";

        private int _originalSize = 0;

        private bool _zipEntryFile = false;

        private bool _temporary = false;
        #endregion �����ϐ�

        #region �v���p�e�B
        /// <summary>
        /// ID
        /// </summary>
        public string Id
        {
            get { return _id; }
            set { _id = value; }
        }

        /// <summary>
        /// OriginalName
        /// </summary>
        public string OriginalName
        {
            get { return _originalName; }
            set { _originalName = value; }
        }

        /// <summary>
        /// FileType
        /// </summary>
        public string FileType
        {
            get { return _fileType; }
            set { _fileType = value; }
        }

        /// <summary>
        /// MimeType
        /// </summary>
        public string MimeType
        {
            get { return _mimeType; }
            set { _mimeType = value; }
        }

        /// <summary>
        /// OriginalSize
        /// </summary>
        public int OriginalSize
        {
            get { return _originalSize; }
            set { _originalSize = value; }
        }

        /// <summary>
        /// is ZipEntryFile
        /// </summary>
        public bool ZipEntryFile
        {
            get { return _zipEntryFile; }
            set { _zipEntryFile = value; }
        }

        /// <summary>
        /// is Temporary
        /// </summary>
        public bool Temporary
        {
            get { return _temporary; }
            set { _temporary = value; }
        }
        #endregion
    }
}
