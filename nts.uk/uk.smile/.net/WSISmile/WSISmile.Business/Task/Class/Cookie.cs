using System;
using System.Collections.Generic;
using System.Net;
using System.Text.RegularExpressions;

using WSISmile.Business.Link.Parameter.Cookie;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// クッキー情報保持用
    /// </summary>
    public class Cookie
    {
        /// <summary>
        /// クッキー情報リスト
        /// </summary>
        public List<CookieInfo> Values = new List<CookieInfo>();

        /// <summary>
        /// クッキー情報を保存
        /// </summary>
        /// <param name="cookies">HttpResponse.Cookies</param>
        public void Save(CookieCollection cookies)
        {
            #region クッキー情報を保存 : Save
            this.Values = new List<CookieInfo>();

            foreach (System.Net.Cookie cookie in cookies)
            {
                this.Values.Add(new CookieInfo(cookie.Name, cookie.Value, cookie.Path, cookie.Domain));
            }
            #endregion
        }

        /// <summary>
        /// クッキー情報を復旧
        /// </summary>
        /// <returns></returns>
        public CookieCollection Retrieve()
        {
            #region クッキー情報を復旧 : Retrieve
            CookieCollection cookies = new CookieCollection();

            foreach (CookieInfo cookieInfo in this.Values)
            {
                cookies.Add(new System.Net.Cookie(cookieInfo.Name, cookieInfo.Value, cookieInfo.Path, cookieInfo.Domain));
            }

            return cookies;
            #endregion
        }

        /// <summary>
        /// クッキー情報を補正
        /// </summary>
        /// <param name="request"></param>
        /// <param name="httpResponse"></param>
        public void Fix(HttpWebRequest request, HttpWebResponse httpResponse)
        {
            #region クッキー情報を補正 : Fix
            string[] cookieValues = httpResponse.Headers.GetValues("Set-Cookie");
            if (cookieValues.Length > 2)
            {
                /*
                 * nts.uk.sescon
                */
                System.Net.Cookie cookie_sescon = ConvertFromString(request, cookieValues[0] + cookieValues[1]);

                /*
                 * JSESSIONID
                */
                System.Net.Cookie cookie_JSESSIONID = ConvertFromString(request, cookieValues[2]);

                CookieCollection cookies = new CookieCollection();
                cookies.Add(cookie_sescon);
                cookies.Add(cookie_JSESSIONID);

                this.Save(cookies);
            }
            #endregion
        }

        /// <summary>
        /// StringからCookieを生成
        /// </summary>
        /// <param name="request"></param>
        /// <param name="cookieValue"></param>
        /// <returns></returns>
        public System.Net.Cookie ConvertFromString(HttpWebRequest request, string cookieValue)
        {
            #region StringからCookieを生成 : ConvertFromString
            Match match = Regex.Match(cookieValue, "(.+?)=(.+?);");

            if (match.Captures.Count == 0)
            {
                return null;
            }

            return new System.Net.Cookie(
                                            match.Groups[1].ToString(), 
                                            match.Groups[2].ToString(), 
                                            "/", 
                                            request.RequestUri.Host.Split(':')[0]
                       );
            #endregion
        }
    }
}
