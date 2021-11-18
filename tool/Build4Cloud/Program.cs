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
            if (args.Length != 1)
            {
                Console.WriteLine("コマンドライン引数でデータソース数を指定してください");
                return;
            }

            var context = new Context
            {
                RootPath = FindRootPathFromCurrent(),
                Projects = new [] { "com", "at", "cloud" },
            };

            int datasourcesCount = int.Parse(args[0]);


            foreach (var project in context.Projects)
            {
                string pathToWeb = Path.Combine(context.RootPath, $"uk.{project}", $"{project}.web", $"nts.uk.{project}.web");

                var xml = new PersistenceXml(context.RootPath, pathToWeb);
                xml.CreateCloudEdition(datasourcesCount);

                Build(pathToWeb);

                xml.RestoreOriginalFile();
            }

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

        private static void Build(string pathToWeb)
        {
            var processStartInfo = new ProcessStartInfo("gradle", "build");
            processStartInfo.WorkingDirectory = pathToWeb;

            var process = Process.Start(processStartInfo);
            process.WaitForExit();
            process.Close();
        }
    }

}

