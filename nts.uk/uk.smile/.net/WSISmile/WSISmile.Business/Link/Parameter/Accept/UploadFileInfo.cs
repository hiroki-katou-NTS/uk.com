using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// ファイル情報(アップロード済み)
    /// </summary>
    public class UploadFileInfo
    {
        #region コンストラクタ
        public UploadFileInfo() { }

        public UploadFileInfo(JObject aJObject)
        {
            this._id = aJObject.GetValue("id").ToObject<string>();
            this._originalName = aJObject.GetValue("originalName").ToObject<string>();
            this._fileType = aJObject.GetValue("fileType").ToObject<string>();
            this._mimeType = aJObject.GetValue("mimeType").ToObject<string>();
            this._originalSize = aJObject.GetValue("originalSize").ToObject<int>();

            this._zipEntryFile = aJObject.GetValue("zipEntryFile").ToObject<bool>();
            this._temporary = aJObject.GetValue("temporary").ToObject<bool>();
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _id = "";

        private string _originalName = "";

        private string _fileType = "";

        private string _mimeType = "";

        private int _originalSize = 0;

        private bool _zipEntryFile = false;

        private bool _temporary = false;
        #endregion 内部変数

        #region プロパティ
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
