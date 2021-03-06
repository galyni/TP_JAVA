USE [Librairie]
GO
/****** Object:  Table [dbo].[Books]    Script Date: 30/01/2021 20:32:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Books](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](50) NOT NULL,
	[EditorsID] [int] NOT NULL,
	[PublishDate] [date] NOT NULL,
	[Borrowed] [bit] NOT NULL,
	[Borrowable] [bit] NULL,
	[NumberOfPages] [smallint] NULL,
	[Type] [tinyint] NULL,
	[Translated] [bit] NULL,
	[Author] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Books] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CDs]    Script Date: 30/01/2021 20:32:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CDs](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](50) NOT NULL,
	[EditorsID] [int] NOT NULL,
	[PublishDate] [date] NOT NULL,
	[Borrowed] [bit] NOT NULL,
	[Borrowable] [bit] NULL,
	[Length] [nvarchar](10) NULL,
	[Type] [tinyint] NULL,
	[NumberOfTracks] [tinyint] NULL,
 CONSTRAINT [PK_CDs] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DVDs]    Script Date: 30/01/2021 20:32:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DVDs](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](50) NOT NULL,
	[EditorsID] [int] NOT NULL,
	[PublishDate] [date] NOT NULL,
	[Borrowed] [bit] NOT NULL,
	[Borrowable] [bit] NULL,
	[Length] [nvarchar](10) NULL,
	[Type] [tinyint] NULL,
	[AudioDescription] [bit] NULL,
 CONSTRAINT [PK_DVDs] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Editors]    Script Date: 30/01/2021 20:32:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Editors](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SIRET] [nvarchar](10) NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Street] [nvarchar](50) NOT NULL,
	[ZipCode] [nvarchar](5) NOT NULL,
	[City] [nvarchar](50) NOT NULL,
	[Country] [nvarchar](50) NULL,
 CONSTRAINT [PK_Editors] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Magazines]    Script Date: 30/01/2021 20:32:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Magazines](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](50) NOT NULL,
	[EditorsID] [int] NOT NULL,
	[PublishDate] [date] NOT NULL,
	[Borrowed] [bit] NOT NULL,
	[Borrowable] [bit] NULL,
	[NumberOfPages] [smallint] NULL,
	[Type] [tinyint] NULL,
	[Frequency] [tinyint] NULL,
	[Author] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Magazines] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Books]  WITH CHECK ADD  CONSTRAINT [FK_Books_Editors] FOREIGN KEY([EditorsID])
REFERENCES [dbo].[Editors] ([ID])
GO
ALTER TABLE [dbo].[Books] CHECK CONSTRAINT [FK_Books_Editors]
GO
ALTER TABLE [dbo].[CDs]  WITH CHECK ADD  CONSTRAINT [FK_CDs_Editors] FOREIGN KEY([EditorsID])
REFERENCES [dbo].[Editors] ([ID])
GO
ALTER TABLE [dbo].[CDs] CHECK CONSTRAINT [FK_CDs_Editors]
GO
ALTER TABLE [dbo].[DVDs]  WITH CHECK ADD  CONSTRAINT [FK_DVDs_Editors] FOREIGN KEY([EditorsID])
REFERENCES [dbo].[Editors] ([ID])
GO
ALTER TABLE [dbo].[DVDs] CHECK CONSTRAINT [FK_DVDs_Editors]
GO
ALTER TABLE [dbo].[Magazines]  WITH CHECK ADD  CONSTRAINT [FK_Magazines_Editors] FOREIGN KEY([EditorsID])
REFERENCES [dbo].[Editors] ([ID])
GO
ALTER TABLE [dbo].[Magazines] CHECK CONSTRAINT [FK_Magazines_Editors]
GO
