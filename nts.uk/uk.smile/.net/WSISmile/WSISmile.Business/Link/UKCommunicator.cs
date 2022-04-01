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
    /// Universal�Ύ��Y Web�T�[�r�X
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
        /// Universal�Ύ��Y�T�[�o Uri(Server Uri)
        /// </summary>
        private readonly Uri serverUri;

        /// <summary>
        /// Universal�Ύ��YWEB-API Uri
        /// </summary>
        private readonly Uri webapiUri;

        /// <summary>
        /// �R���X�g���N�^
        /// </summary>
        public UKCommunicator()
        {
            // Universal�Ύ��Y�T�[�oUri(Server Uri)
            this.serverUri = new Uri(ConfigurationManager.AppSettings["UKServer"]);

            // Universal�Ύ��YWEB-API Uri
            this.webapiUri = new Uri(this.serverUri, WEB_API_PATH);

            // Tls11, Tls12�ڑ����T�|�[�g
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls | (SecurityProtocolType)768 | (SecurityProtocolType)3072;
        }

        /// <summary>
        /// WEB�T�[�r�X (Post����)
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="webApi">WebApi</param>
        /// <param name="appendUri">Get�p�����[�^(�C��)</param>
        /// <param name="postParameter">Post�p�����[�^(JSON�`��)</param>
        /// <param name="responseType">���X�|���X���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>Response(object)</returns>
        public object WebMethod_Post(TaskInfo TI, WebApi webApi, string appendUri, string postParameter, ResponseType responseType, ref string errorMsg)
        {
            #region WEB�T�[�r�X (Post����)
            object response = null;

            try
            {
                // ���N�G�X�g�w�b�_�[
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(webApi) + appendUri));
                request.Method = "POST";
                request.KeepAlive = false;
                //request.Timeout = 300 * 1000; // *�~���b�P�� 300�b

                request.ContentType = "application/json;";
                request.Accept      = "application/json;";

                request.UserAgent = USERAGENT;

                request.ContentLength = Encoding.UTF8.GetBytes(postParameter).Length;

                // �N�b�L�[�Z�b�g
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // �p�����[�^�ݒ�
                {
                    if (postParameter != string.Empty)
                    {
                        using (StreamWriter streamWriter = new StreamWriter(request.GetRequestStream()))
                        {
                            streamWriter.Write(postParameter);
                        }
                    }
                }

                // ���M����M
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode == HttpStatusCode.NoContent)
                        {
                            return null;
                        }

                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API�Ăяo���Ɏ��s���܂����B");
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

                        // Cookie���^�X�N���ɕۑ��A���񃊃N�G�X�g���ɕt������
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

                #region �ڑ��G���[�`�F�b�N
                if (wex.Status == WebExceptionStatus.ConnectFailure)
                {
                    errorMsg += Environment.NewLine + "�Ύ��Y�T�[�o�A�h���X�̎w��Ɍ�肪�Ȃ������m�F���Ă��������B";
                    errorMsg += Environment.NewLine + this.serverUri.AbsoluteUri;
                    return response;
                }
                #endregion

                #region �^�C���A�E�g�`�F�b�N
                if (wex.Status == WebExceptionStatus.Timeout)
                {
                    errorMsg += Environment.NewLine + "�Ύ��Y�A�g�T�[�r�X���ꎞ���~�̉\��������܂��B";
                    errorMsg += Environment.NewLine + this.serverUri.AbsoluteUri;
                    return response;
                }
                #endregion

                #region errorResponse
                if (wex.Response != null)
                {
                    if (((HttpWebResponse)wex.Response).StatusCode == HttpStatusCode.NotFound)
                    {
                        errorMsg = "�Ύ��Y�A�g�T�[�r�X���ꎞ���~�ł��B�Ǘ��҂ɂ��A�����������B";
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

            #region �Z�b�V�����^�C���A�E�g�`�F�b�N
            if (response is JObject)
            {
                JObject jobject = response as JObject;
                if (jobject["sessionTimeout"] != null)
                {
                    if (jobject.GetValue("sessionTimeout").ToObject<bool>())
                    {
                        errorMsg = "�G���[�F�Z�b�V�����^�C���A�E�g�ł����B";
                        return null;
                    }
                }
            }
            #endregion

            return response;
            #endregion WEB�T�[�r�X (Post����)
        }

        /// <summary>
        /// WEB�T�[�r�X (OutputFileDownload)
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="appendUri">FileId</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public string WebMethod_FileDownload(TaskInfo TI, string appendUri, ref string errorMsg)
        {
            #region WEB�T�[�r�X (OutputFileDownload)
            string response = string.Empty;

            try
            {
                // ���N�G�X�g�w�b�_�[
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(WebApi.FileDownload) + appendUri));
                request.Method = "GET";
                request.KeepAlive = false;

                // �N�b�L�[�Z�b�g
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // ���M����M
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API�Ăяo���Ɏ��s���܂����B");
                        }

                        using (StreamReader streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            response = streamReader.ReadToEnd();
                        }

                        // Cookie���^�X�N���ɕۑ��A���񃊃N�G�X�g���ɕt������
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
            #endregion WEB�T�[�r�X (OutputFileDownload)
        }

        /// <summary>
        /// WEB�T�[�r�X (AcceptFileUpload)
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="files">UploadFile.List</param>
        /// <param name="formFields">FormFields</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public JArray WebMethod_FileUpload(TaskInfo TI, List<string> files, NameValueCollection formFields, ref string errorMsg)
        {
            #region WEB�T�[�r�X (AcceptFileUpload)
            JArray response = null;

            try
            {
                string boundary = "----------------------------" + DateTime.Now.Ticks.ToString("x");

                // ���N�G�X�g�w�b�_�[
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(new Uri(webapiUri, WebApiEnumConverter.GetWebApiName(WebApi.FileUpload)));
                request.ContentType = "multipart/form-data; boundary=" + boundary;
                request.Method = "POST";
                request.KeepAlive = true;

                // �N�b�L�[�Z�b�g
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(TI.Cookie.Retrieve());

                // �p�����[�^�ݒ�
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

                // ���M����M
                {
                    using (HttpWebResponse httpResponse = (HttpWebResponse)request.GetResponse())
                    {
                        if (httpResponse.StatusCode != HttpStatusCode.OK)
                        {
                            //throw new Exception("API�Ăяo���Ɏ��s���܂����B");
                        }

                        using (StreamReader streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            response = JArray.Parse(streamReader.ReadToEnd());
                        }

                        // Cookie���^�X�N���ɕۑ��A���񃊃N�G�X�g���ɕt������
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
            #endregion WEB�T�[�r�X (AcceptFileUpload)
        }
    }
}
