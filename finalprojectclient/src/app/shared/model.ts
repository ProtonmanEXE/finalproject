export interface UserLogin {
	userName: string
	password: string
}

export interface UserFullDetails extends UserLogin {
  email: string
}

export interface GameCard {
  name: string
  releasedDate: string
  backgroundImageUrl: string
  esrbRating: string
  genres: string[]
  gameId: number
}

export interface GameDetails extends GameCard {
  description: string
  metacriticRating: number
  metacriticUrl: string
  stores: string[]
  platforms: string[]
}

export interface GameSummary {
  name: string
  releasedDate: string
  backgroundImageUrl: string
  gameId: number
}

export interface ResponseMessage {
  status: string
}
