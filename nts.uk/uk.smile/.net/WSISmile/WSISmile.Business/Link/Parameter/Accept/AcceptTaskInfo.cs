using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace WSISmile.Business.Link.Parameter.Accept
{
    /// <summary>
    /// 外部受入タスク情報
    /// </summary>
    public class AcceptTaskInfo
    {
        #region コンストラクタ
        public AcceptTaskInfo() { }

        public AcceptTaskInfo(JObject aJObject)
        {
            if (aJObject["id"] != null)
            {
                this._taskId = aJObject.GetValue("id").ToObject<string>();
            }

            if (aJObject["status"] != null)
            {
                this._status = aJObject.GetValue("status").ToObject<string>();
            }

            if (aJObject["pending"] != null)
            {
                this._pending = aJObject.GetValue("pending").ToObject<bool>();
            }

            if (aJObject["running"] != null)
            {
                this._running = aJObject.GetValue("running").ToObject<bool>();
            }

            if (aJObject["succeeded"] != null)
            {
                this._succeeded = aJObject.GetValue("succeeded").ToObject<bool>();
            }

            if (aJObject["taskDatas"] != null)
            {
                JArray jarray = aJObject.GetValue("taskDatas").ToObject<JArray>();

                this._taskDatas = new List<AcceptTaskData>();
                foreach (JObject job in jarray)
                {
                    this._taskDatas.Add(new AcceptTaskData(job));
                }
            }

            if (aJObject["error"] != null)
            {
                this._taskError = new AcceptTaskError(aJObject.GetValue("error").ToObject<JObject>());
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _taskId = "";

        private string _status = "";

        private bool _pending = false;

        private bool _running = false;

        private bool _succeeded = false;

        private List<AcceptTaskData> _taskDatas = new List<AcceptTaskData>();

        private AcceptTaskError _taskError = new AcceptTaskError();
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// タスクID
        /// </summary>
        public string TaskId
        {
            get { return _taskId; }
            set { _taskId = value; }
        }

        /// <summary>
        /// Status.
        /// </summary>
        public string Status
        {
            get { return _status; }
            set { _status = value; }
        }

        /// <summary>
        /// 中止中?
        /// </summary>
        public bool Pending
        {
            get { return _pending; }
            set { _pending = value; }
        }

        /// <summary>
        /// 実行中?
        /// </summary>
        public bool Running
        {
            get { return _running; }
            set { _running = value; }
        }

        /// <summary>
        /// 実行成功
        /// </summary>
        public bool Succeeded
        {
            get { return _succeeded; }
            set { _succeeded = value; }
        }

        /// <summary>
        /// タスクデータ
        /// </summary>
        public List<AcceptTaskData> TaskDatas
        {
            get { return _taskDatas; }
            set { _taskDatas = value; }
        }

        /// <summary>
        /// タスクエラー
        /// </summary>
        public AcceptTaskError TaskError
        {
            get { return _taskError; }
            set { _taskError = value; }
        }
        #endregion プロパティ

        #region AcceptTaskで発生したエラー情報を返す
        /// <summary>
        /// AcceptTaskで発生したエラー情報を返す
        /// </summary>
        /// <returns></returns>
        public string GetAcceptTaskError()
        {
            #region AcceptTaskで発生したエラー情報を返す : GetAcceptTaskError
            /*
             * task id.
            */
            string taskId = "task id: " + this._taskId;
            /*
             * status
            */
            string status = "status: " + this._status + " pending: " + (this._pending ? "true" : "false") + " running: " + (this._running ? "true" : "false");

            /*
             * common error (ビジネスエラー)
            */
            string error_com = this.TaskError.MessageId + "： " + this.TaskError.Message;

            /*
             * process error (TaskDataエラー)
            */
            string error_pro = string.Empty;

            bool processFailed = false;
            foreach (AcceptTaskData acceptTaskData in this.TaskDatas)
            {
                // process failed.
                if (acceptTaskData.Key == "process" && acceptTaskData.ValueAsString.IndexOf("failed") > 0)
                {
                    processFailed = true;
                }
            }
            if (processFailed)
            {
                foreach (AcceptTaskData acceptTaskData in this.TaskDatas)
                {
                    // process failed.
                    if (acceptTaskData.Key == "message")
                    {
                        error_pro = acceptTaskData.ValueAsString;
                    }
                }
            }

            return 
                taskId + Environment.NewLine + 
                status + Environment.NewLine  + 
                error_com + Environment.NewLine + 
                error_pro;
            #endregion
        }
        #endregion
    }

    /// <summary>
    /// 外部受入タスク情報-タスクデータ
    /// </summary>
    public class AcceptTaskData
    {
        #region コンストラクタ
        public AcceptTaskData() { }

        public AcceptTaskData(JObject aJObject)
        {
            if (aJObject["key"] != null)
            {
                this._key = aJObject.GetValue("key").ToObject<string>();
            }

            if (aJObject["valueAsString"] != null)
            {
                this._valueAsString = aJObject.GetValue("valueAsString").ToObject<string>();
            }

            if (aJObject["valueAsBoolean"] != null)
            {
                this._valueAsBoolean = aJObject.GetValue("valueAsBoolean").ToObject<bool>();
            }

            if (aJObject["valueAsNumber"] != null)
            {
                string strValue = aJObject.GetValue("valueAsNumber").ToObject<string>();
                int.TryParse(strValue, out this._valueAsNumber);
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _key = "";

        private string _valueAsString = "";

        private bool _valueAsBoolean = false;

        private int _valueAsNumber = 0;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// Key
        /// </summary>
        public string Key
        {
            get { return _key; }
            set { _key = value; }
        }

        /// <summary>
        /// 文字列Value.
        /// </summary>
        public string ValueAsString
        {
            get { return _valueAsString; }
            set { _valueAsString = value; }
        }

        /// <summary>
        /// Bool型Value.
        /// </summary>
        public bool ValueAsBoolean
        {
            get { return _valueAsBoolean; }
            set { _valueAsBoolean = value; }
        }

        /// <summary>
        /// 数字型Value.
        /// </summary>
        public int ValueAsNumber
        {
            get { return _valueAsNumber; }
            set { _valueAsNumber = value; }
        }
        #endregion プロパティ
    }

    /// <summary>
    /// 外部受入タスク情報-タスクエラー
    /// </summary>
    public class AcceptTaskError
    {
        #region コンストラクタ
        public AcceptTaskError() { }

        public AcceptTaskError(JObject aJObject)
        {
            if (aJObject != null)
            {
                if (aJObject["message"] != null)
                {
                    this._message = aJObject.GetValue("message").ToObject<string>();
                }
                if (aJObject["messageId"] != null)
                {
                    this._messageId = aJObject.GetValue("messageId").ToObject<string>();
                }
                if (aJObject["businessException"] != null)
                {
                    this._businessException = aJObject.GetValue("businessException").ToObject<bool>();
                }
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private string _message = "";

        private string _messageId = "";

        private bool _businessException = false;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// Message
        /// </summary>
        public string Message
        {
            get { return _message; }
            set { _message = value; }
        }

        /// <summary>
        /// MessageId
        /// </summary>
        public string MessageId
        {
            get { return _messageId; }
            set { _messageId = value; }
        }

        /// <summary>
        /// is BusinessException
        /// </summary>
        public bool BusinessException
        {
            get { return _businessException; }
            set { _businessException = value; }
        }
        #endregion プロパティ
    }
}
