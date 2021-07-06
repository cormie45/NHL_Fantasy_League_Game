import React, {useState, useEffect, useRef} from 'react'
import LeagueTable from '../components/table/LeagueTable'
import Request from '../helpers/request'

const MainContainer = () => {

    const [teams, setTeams] = useState([])
    const [players, setPlayers] = useState([])
    const [coaches, setCoaches] = useState([])
    const [games, setGames] = useState([])

    const requestAll = function(){
        const request = new Request();
        const teamPromise = request.get('http://localhost:8080/api/teams')
        const playerPromise = request.get('http://localhost:8080/api/players')
        const coachPromise = request.get('http://localhost:8080/api/coaches')
        const gamesPromise = request.get('http://localhost:8080/api/games/create')

        Promise.all([teamPromise, playerPromise, coachPromise, gamesPromise])
        .then((data) => {
            setTeams(data[0])
            setPlayers(data[1])
            setCoaches(data[2])
            setGames(data[3])
        })
    }

    useEffect(() => {
        requestAll()
    }, [])

    if (games.length === 0) {
        return (<p>Loading...</p>)
    }

    return (
        <div>
            <LeagueTable teams={teams}/>
        </div>
    )
}

export default MainContainer;