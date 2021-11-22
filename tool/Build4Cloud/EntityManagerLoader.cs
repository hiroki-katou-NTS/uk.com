﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Build4Cloud
{
    class EntityManagerLoader : TemporarilyEditFile
    {
        public EntityManagerLoader(string rootPath)
            : base(Path.Combine(rootPath, @"uk.com\shr\nts.uk.shr.com\src\main\java\nts\uk\shr\infra\data\CloudEntityManagerLoader.java"))
        {
        }

        protected override void EditFile(int datasourcesCount)
        {
            var lines = File.ReadAllLines(PathToStashed);

            using (var writer = File.CreateText(path))
            {
                bool appended = false;

                foreach (string line in lines)
                {
                    writer.WriteLine(line);

                    if (appended)
                    {
                        continue;
                    }

                    if (line.Contains("private EntityManager entityManager;"))
                    {
                        AppendDataSources(writer, datasourcesCount);
                        appended = true;
                    }
                }
            }
        }

        private void AppendDataSources(StreamWriter writer, int datasourcesCount)
        {
            for (int i = 1; i <= datasourcesCount; i++)
            {
                writer.WriteLine($"@PersistenceContext(unitName = \"UK{i}\")");
                writer.WriteLine($"private EntityManager entityManager{i};");
            }
        }
    }
}
