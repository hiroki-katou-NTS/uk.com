using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Net;
using System.Text;

using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Log;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link
{
    /// <summary>
    /// Universal勤次郎 Webサービス
    /// </summary>
    public class UKCommunicator
    {
        /// <summary>
        /// WEB API Path.
        /// </summary>
        private const string WEB_API_PATH = "/nts.uk.com.web/webapi/";

        /// <summary>
        /// UserAgent
        /// </summary>
        private const string USERAGENT = "UK&Smile Link Client";

        /// <summary>
        /// Universal勤次郎サーバ Uri(Server Uri)
        /// </summary>
        private readonly Uri serverUri;

        /// <summary>
        /// Universal勤次郎WEB-API Uri
        /// </summary>
        private readonly Uri webapiUri;

        /// <summary>
        /// コンストラクタ
        /// </summary>
        public UKCommunicator()
        {
            // Universal勤次郎サーバUri(Server Uri)
            this.serverUri = new Uri(ConfigurationManager.AppSettings["UKServer"]);

            // Universal勤次郎WEB-API Uri
            this.webapiUri = new Uri(this.serverUri, WEB_API_PATH);

            // Tls11, Tls12接続をサポート
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls | (SecurityProtocolType)768 | (SecurityProtocolType)3072;
        }

        /// <summary>
        /// WEBサービス (Post方式)
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="webApi">WebApi</param>
        /// <param name="appendUri">Getパラメータ(任意)</param>
        /// <param name="postParameter">Postパラメータ(JSON形式)</param>
        /// <param name="responseType">レスポンス種類</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>Response(object)</returns>
        public object WebMethod_Post(TaskInfo TI, WebApi webApi, string appendUri, string postParameter, ResponseType responseType, ref string errorMsg)
        {
            #region WEBサービス (Post方式)
            object response = null;

            try
            {
                // リクエストヘッダー
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(webApi) + appendUri));
                request.Method = "POST";
                request.KeepAlive = false;
                //request.Timeout = 300 * 1000; // *ミリ秒単位 300秒

                request.ContentType = "application/json;";
                request.Accept      = "application/json;";

                request.UserAgent = USERAGENT;

                request.ContentLength = Encoding.UTF8.GetBytes(postParameter).Length;

                // クッキーセット
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // パラメータ設定
                {
                    if (postParameter != string.Empty)
                    {
                        using (StreamWriter streamWriter = new StreamWriter(request.GetRequestStream()))
                        {
                            streamWriter.Write(postParameter);
                        }
                    }
                }

                // 送信＆受信
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode == HttpStatusCode.NoContent)
                        {
                            return null;
                        }

                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API呼び出しに失敗しました。");
                        }

                        using (StreamReader streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            switch (responseType)
                            {
                                case ResponseType.JObject:
                                    response = JObject.Parse(streamReader.ReadToEnd());
                                    break;
                                case ResponseType.JArray:
                                    response = JArray.Parse(streamReader.ReadToEnd());
                                    break;
                                case ResponseType.String:
                                    response = streamReader.ReadToEnd();
                                    break;
                                default:
                                    response = null;
                                    break;
                            }
                        }

                        // Cookieをタスク情報に保存、次回リクエスト時に付加する
                        TI.Cookie.Save(httpResponse.Cookies);

                        if (httpResponse.Cookies.Count == 0)
                        {
                            // ?? Max Length Over
                            TI.Cookie.Fix(request, httpResponse);
                        }
                    }
                }
            }
            catch (WebException wex)
            {
                errorMsg = wex.Message;
                response = null;

                #region 接続エラーチェック
                if (wex.Status == WebExceptionStatus.ConnectFailure)
                {
                    errorMsg += Environment.NewLine + "勤次郎サーバアドレスの指定に誤りがないかを確認してください。";
                    errorMsg += Environment.NewLine + this.serverUri.AbsoluteUri;
                    return response;
                }
                #endregion

                #region タイムアウトチェック
                if (wex.Status == WebExceptionStatus.Timeout)
                {
                    errorMsg += Environment.NewLine + "勤次郎連携サービスが一時中止の可能性があります。";
                    errorMsg += Environment.NewLine + this.serverUri.AbsoluteUri;
                    return response;
                }
                #endregion

                #region errorResponse
                if (wex.Response != null)
                {
                    if (((HttpWebResponse)wex.Response).StatusCode == HttpStatusCode.NotFound)
                    {
                        errorMsg = "勤次郎連携サービスが一時中止です。管理者にご連絡ください。";
                        errorMsg += Environment.NewLine + this.serverUri.AbsoluteUri;
                        return response;
                    }

                    using (HttpWebResponse errorResponse = (HttpWebResponse)wex.Response)
                    {
                        using (StreamReader reader = new StreamReader(errorResponse.GetResponseStream()))
                        {
                            JObject error = JObject.Parse(reader.ReadToEnd());
                            errorMsg = error.GetValue("errorMessage").ToObject<string>();

                            string stackTrace = error.GetValue("stackTrace").ToObject<string>();
                            Logger.WriteLog(errorMsg + Environment.NewLine + stackTrace, "WEB API Error.", TI);
                        }
                    }
                }
                #endregion
            }

            #region セッションタイムアウトチェック
            if (response is JObject)
            {
                JObject jobject = response as JObject;
                if (jobject["sessionTimeout"] != null)
                {
                    if (jobject.GetValue("sessionTimeout").ToObject<bool>())
                    {
                        errorMsg = "エラー：セッションタイムアウトでした。";
                        return null;
                    }
                }
            }
            #endregion

            return response;
            #endregion WEBサービス (Post方式)
        }

        /// <summary>
        /// WEBサービス (OutputFileDownload)
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="appendUri">FileId</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public string WebMethod_FileDownload(TaskInfo TI, string appendUri, ref string errorMsg)
        {
            #region WEBサービス (OutputFileDownload)
            string response = string.Empty;

            try
            {
                // リクエストヘッダー
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(WebApi.FileDownload) + appendUri));
                request.Method = "GET";
                request.KeepAlive = false;

                // クッキーセット
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // 送信＆受信
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API呼び出しに失敗しました。");
                        }

                        using (StreamReader streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            response = streamReader.ReadToEnd();
                        }

                        // Cookieをタスク情報に保存、次回リクエスト時に付加する
                        TI.Cookie.Save(httpResponse.Cookies);
                    }
                }
            }
            catch (WebException wex)
            {
                errorMsg = wex.Message;
                response = string.Empty;

                #region errorResponse
                if (wex.Response != null)
                {
                    using (HttpWebResponse errorResponse = (HttpWebResponse)wex.Response)
                    {
                        using (StreamReader reader = new StreamReader(errorResponse.GetResponseStream()))
                        {
                            JObject error = JObject.Parse(reader.ReadToEnd());
                            errorMsg = error.GetValue("errorMessage").ToObject<string>();

                            string stackTrace = error.GetValue("stackTrace").ToObject<string>();
                            Logger.WriteLog(errorMsg + Environment.NewLine + stackTrace, "WEB API Error.", TI);
                        }
                    }
                }
                #endregion
            }

            return response;
            #endregion WEBサービス (OutputFileDownload)
        }

        /// <summary>
        /// WEBサービス (AcceptFileUpload)
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="files">UploadFile.List</param>
        /// <param name="formFields">FormFields</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public JArray WebMethod_FileUpload(TaskInfo TI, List<string> files, NameValueCollection formFields, ref string errorMsg)
        {
            #region WEBサービス (AcceptFileUpload)
            JArray response = null;

            try
            {
                string boundary = "----------------------------" + DateTime.Now.Ticks.ToString("x");

                // リクエストヘッダー
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(WebApi.FileUpload)));
                request.ContentType = "multipart/form-data; boundary=" + boundary;
                request.Method = "POST";
                request.KeepAlive = true;

                // クッキーセット
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // パラメータ設定
                {
                    Stream memStream = new MemoryStream();

                    #region Write Form Fields
                    string formdataTemplate = "\r\n--" + boundary + "\r\nContent-Disposition: form-data; name=\"{0}\";\r\n\r\n{1}";

                    if (formFields != null)
                    {
                        foreach (string key in formFields.Keys)
                        {
                            string formitem = string.Format(formdataTemplate, key, formFields[key]);
                            byte[] formitembytes = Encoding.UTF8.GetBytes(formitem);
                            memStream.Write(formitembytes, 0, formitembytes.Length);
                        }
                    }
                    #endregion Write Form Fields

                    #region Write Files
                    /*
                    string headerTemplate =
                        "Content-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"\r\n" +
                        "Content-Type: application/octet-stream\r\n\r\n";
                    */

                    //byte[] boundarybytes = Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");
                    byte[] endBoundbytes = Encoding.ASCII.GetBytes("\r\n--" + boundary + "--");

                    foreach (string file in files)
                    {
                        /*
                        memStream.Write(boundarybytes, 0, boundarybytes.Length);
                        string header = string.Format(headerTemplate, "file", Path.GetFileName(file));
                        byte[] headerbytes = Encoding.UTF8.GetBytes(header);

                        memStream.Write(headerbytes, 0, headerbytes.Length);
                        */

                        using (FileStream fileStream = new FileStream(file, FileMode.Open, FileAccess.Read))
                        {
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0)
                            {
                                memStream.Write(buffer, 0, bytesRead);
                            }
                        }
                    }

                    memStream.Write(endBoundbytes, 0, endBoundbytes.Length);
                    #endregion Write Files

                    request.ContentLength = memStream.Length;

                    using (Stream requestStream = request.GetRequestStream())
                    {
                        memStream.Position = 0;
                        byte[] tempBuffer = new byte[memStream.Length];
                        memStream.Read(tempBuffer, 0, tempBuffer.Length);
                        memStream.Close();
                        requestStream.Write(tempBuffer, 0, tempBuffer.Length);
                    }
                }

                // 送信＆受信
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API呼び出しに失敗しました。");
                        }

                        using (StreamReader streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            response = JArray.Parse(streamReader.ReadToEnd());
                        }

                        // Cookieをタスク情報に保存、次回リクエスト時に付加する
                        TI.Cookie.Save(httpResponse.Cookies);
                    }
                }
            }
            catch (WebException wex)
            {
                errorMsg = wex.Message;
                response = null;

                #region errorResponse
                if (wex.Response != null)
                {
                    using (HttpWebResponse errorResponse = (HttpWebResponse)wex.Response)
                    {
                        using (StreamReader reader = new StreamReader(errorResponse.GetResponseStream()))
                        {
                            JObject error = JObject.Parse(reader.ReadToEnd());
                            errorMsg = error.GetValue("errorMessage").ToObject<string>();

                            string stackTrace = error.GetValue("stackTrace").ToObject<string>();
                            Logger.WriteLog(errorMsg + Environment.NewLine + stackTrace, "WEB API Error.", TI);
                        }
                    }
                }
                #endregion
            }

            return response;
            #endregion WEBサービス (AcceptFileUpload)
        }
    }
}
