USE [Librairie]
GO
SET IDENTITY_INSERT [dbo].[Editors] ON 

INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1354, N'un siret', N'un nom', N'une rue', N'38000', N'une ville', N'un pays')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1355, N'Siret', N'chez un editeur', N'quelque part', N'38000', N'dans une ville lointaine', N'en Europe')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1356, N'qui sait?', N'va savoir', N'paumé', N'38000', N'j''en sais rien', N'sur un continent')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1357, N'nope', N'hippies&Co', N'est ce important?', N'38000', N'pas d''inspiration', N'USA')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1358, N'0000000000', N'last survivor editions', N'proche du centre de la Terre', N'38000', N'Zion', N'unknown')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1359, N'9543186542', N'CD projekts', N'dans un garage', N'38000', N'Wroclaw', N'Pologne')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1360, N'9831546785', N'le manchot pas droit', N'pôle Sud', N'38000', N'panguoria', N'extreme South')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1361, N'9012864856', N'skaterzz', N'9 rue du skate park', N'38000', N'Portland', N'USA')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1362, N'1547926875', N'AutoMoto&Co', N'rue du vroum', N'38000', N'Monaco', N'France')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1363, N'5987621468', N'ScienceEditor', N'avenue des sciences', N'38000', N'Paris', N'France')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1364, N'8943201547', N'edition Jean Moulin', N'boulevard de la resistance', N'38000', N'Grenoble', N'France')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1365, N'9032105478', N'edition vieille', N'ruelle du musée', N'38000', N'Orléans', N'France')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1366, N'6751398542', N'Adibou Ausgaben', N'impasse élémentaire', N'38000', N'Dusseldorf', N'Allemagne')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1367, N'9780230145', N'Silicon Valley', N'85 show pimp street', N'38000', N'Miami', N'USA')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1368, N'0024556314', N'destroy everything editions', N'somewhere on Punk avenue', N'38000', N'Los Angeles', N'USA')
INSERT [dbo].[Editors] ([ID], [SIRET], [Name], [Street], [ZipCode], [City], [Country]) VALUES (1369, N'9999999999', N'Deus edition', N'ruelle de la sérénité', N'38000', N'Utopia', N'Heaven')
SET IDENTITY_INSERT [dbo].[Editors] OFF
GO
SET IDENTITY_INSERT [dbo].[Magazines] ON 

INSERT [dbo].[Magazines] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Frequency], [Author]) VALUES (302, N'Auto moto', 1362, CAST(N'2021-01-30' AS Date), 0, 0, 50, 5, 2, N'Les magazines qui roulent')
INSERT [dbo].[Magazines] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Frequency], [Author]) VALUES (303, N'Science et vie', 1363, CAST(N'2021-01-30' AS Date), 0, 0, 75, 1, 3, N'Les magazines de sciences')
INSERT [dbo].[Magazines] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Frequency], [Author]) VALUES (304, N'Le Dauphiné libéré', 1364, CAST(N'2021-01-30' AS Date), 0, 0, 12, 4, 0, N'Journal du dauphiné')
INSERT [dbo].[Magazines] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Frequency], [Author]) VALUES (305, N'un magazine d''histoire', 1365, CAST(N'2021-01-30' AS Date), 0, 0, 36, 2, 4, N'Journal du musée')
SET IDENTITY_INSERT [dbo].[Magazines] OFF
GO
SET IDENTITY_INSERT [dbo].[Books] ON 

INSERT [dbo].[Books] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Translated], [Author]) VALUES (250, N'Design Pattern', 1354, CAST(N'2021-01-30' AS Date), 0, 1, 300, 1, 1, N'Eric Gamma')
INSERT [dbo].[Books] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Translated], [Author]) VALUES (251, N'L''art d''avoir toujours raison', 1355, CAST(N'2021-01-30' AS Date), 0, 1, 75, 4, 1, N'Arthur Schöpenhauer')
INSERT [dbo].[Books] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Translated], [Author]) VALUES (252, N'Le grands livre des robots', 1356, CAST(N'2021-01-30' AS Date), 0, 1, 2000, 0, 1, N'Isaac Asimov')
INSERT [dbo].[Books] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [NumberOfPages], [Type], [Translated], [Author]) VALUES (253, N'Les portes de la perception', 1357, CAST(N'2021-01-30' AS Date), 0, 1, 215, 2, 1, N'Aldous Huxley')
SET IDENTITY_INSERT [dbo].[Books] OFF
GO
SET IDENTITY_INSERT [dbo].[CDs] ON 

INSERT [dbo].[CDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [NumberOfTracks]) VALUES (216, N'Adibou à l''école', 1366, CAST(N'2021-01-30' AS Date), 0, 1, N'36:45', 3, 10)
INSERT [dbo].[CDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [NumberOfTracks]) VALUES (217, N'Photoshop', 1367, CAST(N'2021-01-30' AS Date), 0, 1, N'48:04', 4, 12)
INSERT [dbo].[CDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [NumberOfTracks]) VALUES (218, N'The Offspring - Americana', 1368, CAST(N'2021-01-30' AS Date), 0, 1, N'58:02', 0, 15)
INSERT [dbo].[CDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [NumberOfTracks]) VALUES (219, N'Forest ambiance', 1369, CAST(N'2021-01-30' AS Date), 0, 1, N'08:45:12', 2, 1)
SET IDENTITY_INSERT [dbo].[CDs] OFF
GO
SET IDENTITY_INSERT [dbo].[DVDs] ON 

INSERT [dbo].[DVDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [AudioDescription]) VALUES (207, N'Matrix', 1358, CAST(N'2021-01-30' AS Date), 0, 1, N'2:17:05', 0, 1)
INSERT [dbo].[DVDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [AudioDescription]) VALUES (208, N'Witcher', 1359, CAST(N'2021-01-30' AS Date), 0, 1, N'1:12:14', 3, 0)
INSERT [dbo].[DVDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [AudioDescription]) VALUES (209, N'La marche des empereurs', 1360, CAST(N'2021-01-30' AS Date), 0, 1, N'14:05', 4, 1)
INSERT [dbo].[DVDs] ([ID], [Title], [EditorsID], [PublishDate], [Borrowed], [Borrowable], [Length], [Type], [AudioDescription]) VALUES (210, N'Deftones - Live in Sydney', 1361, CAST(N'2021-01-30' AS Date), 0, 1, N'11:11', 1, 1)
SET IDENTITY_INSERT [dbo].[DVDs] OFF
GO
