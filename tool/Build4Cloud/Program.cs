using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Build4Cloud
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("コマンドライン引数でプロジェクト（comやatなど）を指定してください");
                return;
            }

            var context = new Context
            {
                RootPath = FindRootPathFromCurrent(),
                Project = args[0],  // "com" | "at" | ...
            };

            var xml = new PersistenceXml(context.GetPathToPersistenceXml());

            xml.CreateCloudEdition(2);

            Build(context);

            xml.RestoreOriginalFile();
        }

        private static string FindRootPathFromCurrent()
        {
            for (string dir = Environment.CurrentDirectory; Directory.Exists(dir); dir = Path.Combine(dir, ".."))
            {
                string root = Path.Combine(dir, "nts.uk");
                if (Directory.Exists(root))
                {
                    return root;
                }
            }

            throw new Exception("root not found: " + Environment.CurrentDirectory);
        }

        private static void Build(Context context)
        {
            var processStartInfo = new ProcessStartInfo("gradle", "build");
            processStartInfo.WorkingDirectory = context.GetPathToWeb();

            var process = Process.Start(processStartInfo);
            process.WaitForExit();
            process.Close();
        }
    }

}

